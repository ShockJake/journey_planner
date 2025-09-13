package io.jp.services.route.generation;

import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.cache.CachedMunicipalitiesProvider;
import io.jp.cache.CachedRouteProvider;
import io.jp.core.domain.place.Place;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteLongevity;
import io.jp.core.organizer.PlaceOrganizer;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceType;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.jp.core.geometricmedian.GeometricMedianVector.calculateGeometricMedian;
import static io.jp.database.entities.route.PlaceType.additionalPlaceTypes;
import static io.jp.mapper.point.PointMapper.mapTo2DArray;
import static io.jp.utils.DateTimeUtils.toOutputDateTime;
import static io.jp.utils.MappingUtils.mapDataToDescription;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.getLength;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
public class RouteGenerationServiceOpt implements RouteGenerationService<Route> {
    private final CachedMunicipalitiesProvider cachedMunicipalitiesProvider;
    private final CachedRouteProvider cachedRouteProvider;
    private final DataProvider<PlacesResponse> placesDataProvider;
    private final PlaceOrganizer placeOrganizer;
    private final List<PlaceType> additionalPlaceTypes = additionalPlaceTypes();


    @Override
    public RouteGenerationMetadata getRouteGenerationMetadata() {
        var municipalities = cachedMunicipalitiesProvider.getCachedMunicipalities().stream()
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
        var placeTypes = routeGenerationRequest.places().toArray(new PlaceType[0]);
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
        cachedRouteProvider.putCachedRoute(routeName, result);
        return result;
    }

    private Place[] getPlaces(PlaceType[] placeTypes, String municipalityName, RouteLongevity routeLongevity) {
        var municipality = cachedMunicipalitiesProvider.getCachedMunicipality(municipalityName);
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

    private Place[][] splitPlaces(Place[] places, RouteLongevity routeLongevity, PlaceType[] requiredPlaceTypes) {
        log.info("Splitting places with size {}", places.length);
        var mainPlaces = ArrayUtils.newInstance(Place.class, 0);
        var additionalPlaces = ArrayUtils.newInstance(Place.class, 0);
        var requiredPlacesNumber = routeLongevity.getNumberOfPlaces();

        Stream.of(places).forEach(place -> {
            if (contains(requiredPlaceTypes, place.placeType()) && mainPlaces.length < requiredPlacesNumber) {
                ArrayUtils.add(mainPlaces, place);
            } else if (additionalPlaceTypes.contains(place.placeType())) {
                ArrayUtils.add(additionalPlaces, place);
            }
        });

        if (isEmpty(mainPlaces) || mainPlaces.length != requiredPlacesNumber) {
            for (int i = 0; i < getLength(additionalPlaces); i += 2) {
                var place = additionalPlaces[i];
                ArrayUtils.add(mainPlaces, place);
                ArrayUtils.remove(additionalPlaces, i);
            }
        }

        log.info("Main places - {}, additional places - {}", mainPlaces.length, additionalPlaces.length);
        return new Place[][]{mainPlaces, additionalPlaces};
    }
}
