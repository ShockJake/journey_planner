package io.jp.database.init;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.GeometricMedian;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.PlaceType;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.route.RoutePlace;
import io.jp.database.entities.route.RouteType;
import io.jp.database.repositories.PlaceRepository;
import io.jp.database.repositories.RouteRepository;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


import static io.jp.database.entities.route.RouteType.PREDEFINED;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteDataInit implements ApplicationRunner {
    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final ObjectMapper objectMapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (routeRepository.count() == 0) {
            log.info("Populating routes");
            routeRepository.saveAll(prepareRoutes());
        }
    }

    private List<RouteJpa> prepareRoutes() {
        try {
            var inputData = fileUtils.readFile("/data/mocked/initial_routes.json");
            var rootNode = objectMapper.readTree(inputData);
            var routesNode = rootNode.get("routes");
            if (!routesNode.isArray()) {
                throw new RuntimeException("No routes found");
            }
            return stream(routesNode.spliterator(), false)
                    .map(this::parseRoute)
                    .toList();
        } catch (Exception e) {
            log.error("Cannot populate routes: {}", e.getMessage());
        }
        return List.of();
    }

    private RouteJpa parseRoute(JsonNode routeNode) {
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

        var centerData = GeometricMedian.calculate(places.stream()
                .map(place -> new GeometricMedian.Point(place.getLatitude(), place.getLongitude()))
                .toList());

        var route = mapRoute(routeNode, centerData);
        var routePlaceData = range(0, allPlaces.size()).mapToObj(index -> {
                    var place = allPlaces.get(index);
                    return RoutePlace.builder().placeIndex(index).place(place).route(route).build();
                })
                .toList();
        route.setPlaces(routePlaceData);

        return route;
    }

    private RouteJpa mapRoute(JsonNode routeNode, GeometricMedian.Point centerData) {
        return RouteJpa.builder()
                .name(routeNode.get("name").asText())
                .municipality(routeNode.get("city").asText())
                .description(routeNode.get("description").asText())
                .imageUrl(routeNode.get("imageUrl").asText())
                .centerLatitude(centerData.getX())
                .centerLongitude(centerData.getY())
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
