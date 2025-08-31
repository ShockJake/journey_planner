package io.jp.services.route.generation;

import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.core.domain.place.Place;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteLongevity;
import io.jp.core.organizer.PlaceOrganizer;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceType;
import io.jp.database.repositories.place.MunicipalityRepository;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.jp.cache.CachedMunicipalitiesProvider.getCachedMunicipalities;
import static io.jp.cache.CachedMunicipalitiesProvider.getCachedMunicipality;
import static io.jp.cache.CachedMunicipalitiesProvider.hasNoCachedMunicipalities;
import static io.jp.cache.CachedMunicipalitiesProvider.putCachedMunicipalities;
import static io.jp.cache.CachedRouteProvider.putCachedRoute;
import static io.jp.core.geometricmedian.GeometricMedianVector.calculateGeometricMedian;
import static io.jp.database.entities.route.PlaceType.additionalPlaceTypes;
import static io.jp.mapper.point.PointMapper.mapTo2DArray;
import static io.jp.utils.DateTimeUtils.toOutputDateTime;
import static io.jp.utils.MappingUtils.mapDataToDescription;

@Slf4j
@RequiredArgsConstructor
public class RouteGenerationServiceOpt implements RouteGenerationService<Route> {
    private final MunicipalityRepository municipalityRepository;
    private final DataProvider<PlacesResponse> placesDataProvider;
    private final PlaceOrganizer placeOrganizer;
    private final List<PlaceType> additionalPlaceTypes = additionalPlaceTypes();


    @Override
    public RouteGenerationMetadata getRouteGenerationMetadata() {
        if (hasNoCachedMunicipalities()) {
            log.info("No cached municipalities found, populating from database.");
            putCachedMunicipalities(municipalityRepository.findAll());
        }

        var municipalities = getCachedMunicipalities().stream()
                .map(Municipality::getName)
                .toList();

        return RouteGenerationMetadata.builder()
                .municipalities(municipalities)
                .placeTypes(Arrays.stream(PlaceType.values()).toList())
                .build();
    }

    @Override
    public Route generateRoute(RouteGenerationRequest routeGenerationRequest) {
        var municipalityName = routeGenerationRequest.municipality();
        var placeTypes = routeGenerationRequest.places();
        var routeLongevity = routeGenerationRequest.routeLongevity();
        var creationDateTime = toOutputDateTime(routeGenerationRequest.creationDateTime());

        var places = getPlaces(placeTypes, municipalityName, routeLongevity);
        var routeName = formatRouteName(routeLongevity, municipalityName, creationDateTime);
        var description = mapDataToDescription(municipalityName, placeTypes);
        var placesPositions = mapTo2DArray(places);
        var center = calculateGeometricMedian(placesPositions[0], placesPositions[1]);
        var placesSplit = splitPlaces(places, routeLongevity, placeTypes);

        var result = Route.builder()
                .name(routeName)
                .description(description)
                .imageUrl(placeToImageUrl.get(municipalityName))
                .municipality(municipalityName)
                .places(placesSplit[0])
                .additionalPlaces(placesSplit[0])
                .centerLatitude(center.x)
                .centerLongitude(center.y)
                .build();
        putCachedRoute(routeName, result);
        return result;
    }

    private List<Place> getPlaces(List<PlaceType> placeTypes, String municipalityName, RouteLongevity routeLongevity) {
        var municipality = getCachedMunicipality(municipalityName);
        var places = placesDataProvider.getData(Map.of("placeTypes", placeTypes,
                "latitude", municipality.getLatitude(), "longitude", municipality.getLongitude(),
                "longevity", routeLongevity)).getPlaces();
        placeOrganizer.organize(places);
        return places;
    }

    private String formatRouteName(RouteLongevity routeLongevity, String municipalityName, String creationDateTime) {
        var longevityName = routeLongevity.name().toLowerCase();
        return "A %s trip to %s (Generated[%s])".formatted(longevityName, municipalityName, creationDateTime);
    }

    private Place[][] splitPlaces(List<Place> places, RouteLongevity routeLongevity, List<PlaceType> requiredPlaceTypes) {
        log.info("Splitting places with size {}", places.size());
        var mainPlaces = new ArrayList<Place>();
        var additionalPlaces = new ArrayList<Place>();
        var requiredPlacesNumber = routeLongevity.getNumberOfPlaces();

        places.forEach(place -> {
            if (requiredPlaceTypes.contains(place.placeType()) && mainPlaces.size() < requiredPlacesNumber) {
                mainPlaces.add(place);
            } else if (additionalPlaceTypes.contains(place.placeType())) {
                additionalPlaces.add(place);
            }
        });

        if (mainPlaces.isEmpty() || mainPlaces.size() != requiredPlacesNumber) {
            for (int i = 0; i < additionalPlaces.size(); i += 2) {
                mainPlaces.add(additionalPlaces.remove(i));

            }
        }

        log.info("Main places - {}, additional places - {}", mainPlaces.size(), additionalPlaces.size());
        return new Place[][]{mainPlaces.toArray(new Place[0]), additionalPlaces.toArray(new Place[0])};
    }
}
