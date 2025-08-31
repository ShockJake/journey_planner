package io.jp.services.route.generation;

import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.core.domain.place.Place;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.core.domain.route.RouteLongevity;
import io.jp.core.organizer.PlaceOrganizerBoxed;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceType;
import io.jp.database.repositories.place.MunicipalityRepository;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponseBoxed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.jp.cache.CachedRouteProvider.putCachedRoute;
import static io.jp.core.geometricmedian.GeometricMedian.calculateGeometricMedian;
import static io.jp.database.entities.route.PlaceType.additionalPlaceTypes;
import static io.jp.utils.DateTimeUtils.toOutputDateTime;
import static io.jp.utils.MappingUtils.mapDataToDescription;
import static io.jp.mapper.point.PointMapper.mapToPoints;

@Slf4j
@RequiredArgsConstructor
public class RouteGenerationServiceNotOpt implements RouteGenerationService<RouteBoxed> {
    private final MunicipalityRepository municipalityRepository;
    private final DataProvider<PlacesResponseBoxed> placesDataProvider;
    private final PlaceOrganizerBoxed placeOrganizer;

    public RouteGenerationMetadata getRouteGenerationMetadata() {
        var municipalities = municipalityRepository.findAll().stream()
                .map(Municipality::getName)
                .toList();

        return RouteGenerationMetadata.builder()
                .municipalities(municipalities)
                .placeTypes(Arrays.stream(PlaceType.values()).toList())
                .build();
    }

    public RouteBoxed generateRoute(RouteGenerationRequest routeGenerationRequest) {
        var municipality = municipalityRepository.findByName(routeGenerationRequest.municipality())
                .orElseThrow(() -> new RuntimeException("Municipality %s not found".formatted(routeGenerationRequest.municipality())));
        var places = placesDataProvider.getData(Map.of("placeTypes", routeGenerationRequest.places(),
                        "location", PointBoxed.of(municipality.getLatitude(), municipality.getLongitude()),
                        "longevity", routeGenerationRequest.routeLongevity()))
                .getPlaces();
        placeOrganizer.organize(places);

        var routeName = "A %s trip to %s (Generated[%s])"
                .formatted(routeGenerationRequest.routeLongevity().name().toLowerCase(), routeGenerationRequest.municipality(),
                        toOutputDateTime(routeGenerationRequest.creationDateTime()));
        var description = mapDataToDescription(routeGenerationRequest.municipality(), routeGenerationRequest.places());
        var center = calculateGeometricMedian(mapToPoints(places));
        var split = splitPlaces(places, routeGenerationRequest.routeLongevity(), routeGenerationRequest.places());
        var result = RouteBoxed.builder()
                .name(routeName)
                .municipality(municipality.getName())
                .description(description)
                .imageUrl(placeToImageUrl.get(municipality.getName()))
                .municipality(routeGenerationRequest.municipality())
                .places(split.getFirst())
                .additionalPlaces(split.getLast())
                .center(PointBoxed.of(center.x, center.y))
                .build();
        putCachedRoute(routeName, result);
        return result;
    }

    private List<List<PlaceBoxed>> splitPlaces(List<PlaceBoxed> places, RouteLongevity routeLongevity, List<PlaceType> requiredPlaceTypes) {
        log.info("Splitting places with size {}", places.size());
        var mainPlaces = new ArrayList<PlaceBoxed>();
        var additionalPlaces = new ArrayList<PlaceBoxed>();
        var requiredPlacesNumber = routeLongevity.getNumberOfPlaces();

        places.forEach(place -> {
            if (requiredPlaceTypes.contains(place.placeType()) && mainPlaces.size() < requiredPlacesNumber) {
                mainPlaces.add(place);
            } else if (additionalPlaceTypes().contains(place.placeType())) {
                additionalPlaces.add(place);
            }
        });

        if (mainPlaces.isEmpty() || mainPlaces.size() != requiredPlacesNumber) {
            for (int i = 0; i < additionalPlaces.size(); i += 2) {
                mainPlaces.add(additionalPlaces.remove(i));

            }
        }

        log.info("Main places - {}, additional places - {}", mainPlaces.size(), additionalPlaces.size());
        return List.of(mainPlaces, additionalPlaces);
    }
}
