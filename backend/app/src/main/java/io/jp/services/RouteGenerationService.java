package io.jp.services;

import io.jp.api.dto.RouteGenerationMetadata;
import io.jp.api.dto.RouteGenerationRequest;
import io.jp.core.domain.Point;
import io.jp.core.domain.Route;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceType;
import io.jp.database.repositories.place.MunicipalityRepository;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.jp.cache.CachedMunicipalitiesProvider.getCachedMunicipalities;
import static io.jp.cache.CachedMunicipalitiesProvider.getCachedMunicipality;
import static io.jp.cache.CachedMunicipalitiesProvider.hasNoCachedMunicipalities;
import static io.jp.cache.CachedMunicipalitiesProvider.putCachedMunicipalities;
import static io.jp.cache.CachedRouteProvider.putCachedRoute;
import static io.jp.core.GeometricMedian.calculateGeometricMedian;
import static io.jp.utils.MappingUtils.mapDataToDescription;
import static io.jp.utils.MappingUtils.mapToPoints;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteGenerationService {
    private final MunicipalityRepository municipalityRepository;
    private final DataProvider<PlacesResponse> placesDataProvider;

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

    public Route generateRoute(RouteGenerationRequest routeGenerationRequest) {
        var municipalityName = routeGenerationRequest.municipality();
        var placeTypes = routeGenerationRequest.places();
        var routeLongevity = routeGenerationRequest.routeLongevity();
        var creationDateTime = routeGenerationRequest.creationDateTime();

        var municipality = getCachedMunicipality(municipalityName);
        var point = Point.of(municipality.getLatitude(), municipality.getLongitude());
        var places = placesDataProvider.getData(Map.of("placeTypes", placeTypes, "location", point,
                "longevity", routeLongevity)).getPlaces();
        var longevity = routeLongevity.name().toLowerCase();

        var routeName = "A %s trip to %s (Generated[%s])".formatted(longevity, municipalityName, creationDateTime);
        var description = mapDataToDescription(municipalityName, placeTypes);
        var center = calculateGeometricMedian(mapToPoints(places));

        var result = Route.builder()
                .name(routeName)
                .description(description)
                .imageUrl("https://www.adventurouskate.com/wp-content/uploads/2017/01/DSCF9597.jpg")
                .municipality(municipalityName)
                .places(places)
                .additionalPlaces(List.of())
                .center(Point.of(center.getX(), center.getY()))
                .build();
        putCachedRoute(routeName, result);
        return result;
    }
}
