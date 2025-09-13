package io.jp.database.init;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.cache.CachedMunicipalitiesProvider;
import io.jp.core.geometricmedian.Point;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.PlaceType;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.repositories.place.MunicipalityRepository;
import io.jp.database.repositories.place.PlaceRepository;
import io.jp.database.repositories.route.RouteRepository;
import io.jp.mapper.route.RoutePlaceMapper;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jp.core.geometricmedian.GeometricMedian.calculateGeometricMedian;
import static io.jp.database.entities.route.RouteType.PREDEFINED;
import static io.jp.mapper.point.PointMapper.mapToPointsFromJpa;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteDataInit implements ApplicationRunner {
    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final MunicipalityRepository municipalityRepository;
    private final CachedMunicipalitiesProvider cachedMunicipalitiesProvider;
    private final ObjectMapper objectMapper;
    private final FileUtils fileUtils = new FileUtils();
    private final RoutePlaceMapper routePlaceMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (routeRepository.count() == 0) {
            log.info("Populating routes");
            Map<String, Municipality> municipalities = prepareMunicipalities();
            cachedMunicipalitiesProvider.putCachedMunicipalities(municipalities.values());
            routeRepository.saveAll(prepareRoutes(municipalities));
            log.info("{} Routes populated", routeRepository.count());
        }
    }

    private List<RouteJpa> prepareRoutes(Map<String, Municipality> municipalities) {
        try {
            var inputData = fileUtils.readFile("/data/mocked/initial_routes.json");
            var rootNode = objectMapper.readTree(inputData);
            var routesNode = rootNode.get("routes");
            if (!routesNode.isArray()) {
                throw new RuntimeException("No routes found");
            }
            return stream(routesNode.spliterator(), false)
                    .map(node -> parseRoute(node, municipalities))
                    .toList();
        } catch (Exception e) {
            log.error("Cannot populate routes: {}", e.getMessage());
        }
        return List.of();
    }

    private Map<String, Municipality> prepareMunicipalities() {
        try {
            var inputData = fileUtils.readFile("/data/initial-data/municipalities.json");
            var rootNode = objectMapper.readTree(inputData);
            if (!rootNode.isArray()) {
                throw new RuntimeException("Not an array node");
            }

            var municipalities = stream(rootNode.spliterator(), false)
                    .map(item -> Municipality.builder()
                            .name(item.get("name").asText())
                            .latitude(item.get("lat").asDouble())
                            .longitude(item.get("lng").asDouble())
                            .build())
                    .toList();
            log.info("Saving municipalities: {}", municipalities);
            var municipalitiesWithId = municipalityRepository.saveAll(municipalities);

            return municipalitiesWithId.stream()
                    .collect(Collectors.toMap(Municipality::getName, item -> item));
        } catch (Exception e) {
            log.error("Cannot parse municipalities: {}", e.getMessage());
        }
        return Map.of();
    }

    private RouteJpa parseRoute(JsonNode routeNode, Map<String, Municipality> municipalities) {
        var placesData = routeNode.get("places");
        var additionalPlacesData = routeNode.get("additionalPlaces");
        if (!placesData.isArray()) {
            throw new RuntimeException("No places found");
        }
        var places = stream(placesData.spliterator(), false)
                .map(place -> parsePlace(place, false))
                .toList();
        var additionalPlaces = stream(additionalPlacesData.spliterator(), false)
                .map(place -> parsePlace(place, true))
                .toList();
        var allPlaces = concat(places.stream(), additionalPlaces.stream()).toList();
        placeRepository.saveAll(allPlaces);

        var centerData = calculateGeometricMedian(mapToPointsFromJpa(allPlaces));
        var municipality = municipalities.get(routeNode.get("city").asText());

        var route = mapRoute(routeNode, centerData, municipality);
        var routePlaceData = range(0, allPlaces.size()).mapToObj(index ->
                        routePlaceMapper.mapToRoutePlace(route, allPlaces.get(index), index))
                .toList();
        route.setPlaces(routePlaceData);

        return route;
    }

    private RouteJpa mapRoute(JsonNode routeNode, Point centerData, Municipality municipality) {
        return RouteJpa.builder()
                .name(routeNode.get("name").asText())
                .municipality(municipality)
                .description(routeNode.get("description").asText())
                .imageUrl(routeNode.get("imageUrl").asText())
                .centerLatitude(centerData.x)
                .centerLongitude(centerData.y)
                .routeType(PREDEFINED)
                .build();
    }

    private PlaceJpa parsePlace(JsonNode placeNode, boolean isAdditional) {
        return PlaceJpa.builder()
                .name(placeNode.get("name").asText())
                .latitude(placeNode.get("coordinates").get(0).asDouble())
                .longitude(placeNode.get("coordinates").get(1).asDouble())
                .type(PlaceType.valueOf(placeNode.get("type").asText()))
                .isAdditional(isAdditional)
                .build();
    }
}
