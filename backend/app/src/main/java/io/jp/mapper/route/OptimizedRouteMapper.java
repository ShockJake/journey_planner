package io.jp.mapper.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.core.domain.path.PathBoxed;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.core.domain.weather.info.WeatherInfoBoxed;
import io.jp.database.entities.route.OptimizedRouteJpa;
import io.jp.database.entities.route.PlaceType;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.user.User;
import io.jp.mapper.place.BoxedPlaceJpaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.jp.utils.MappingUtils.readObjectFromString;
import static io.jp.utils.MappingUtils.writeObjectToString;
import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class OptimizedRouteMapper {
    private final BoxedRouteJpaMapper routeJpaMapper;
    private final BoxedPlaceJpaMapper placeJpaMapper;
    private final ObjectMapper mapper;

    public OptimizedRouteBoxed mapFromJpa(OptimizedRouteJpa optimizedRouteJpa) {
        log.debug("Mapping optimized route '{}'", optimizedRouteJpa.getRoute().getName());
        var jpaRoute = optimizedRouteJpa.getRoute();
        var jpaPlaces = placeJpaMapper.mapJpaFromRoutePlaces(jpaRoute.getPlaces());
        var route = routeJpaMapper.mapFromJpa(jpaRoute, jpaPlaces);

        var placesOverride = readPlacesOverrides(optimizedRouteJpa.getPlacesOverrides());
        log.debug("{}", placesOverride);
        route.updatePlaces(placesOverride);

        return OptimizedRouteBoxed.builder()
                .optimizationId(optimizedRouteJpa.getOptimizationId())
                .route(route)
                .path(readPath(optimizedRouteJpa.getPath()))
                .weatherInfo(readWeatherInfo(optimizedRouteJpa.getWeatherInfo()))
                .build();
    }

    public OptimizedRouteJpa mapToJpa(OptimizedRouteBoxed optimizedRoute, RouteJpa routeJpa, User user) {
        var placesOverrides = writeObjectToString(mapper, optimizedRoute.route().places());
        var path = writeObjectToString(mapper, optimizedRoute.path());
        var weatherInfo = writeObjectToString(mapper, optimizedRoute.weatherInfo());
        return OptimizedRouteJpa.builder()
                .optimizationId(optimizedRoute.optimizationId())
                .route(routeJpa)
                .placesOverrides(placesOverrides)
                .path(path)
                .weatherInfo(weatherInfo)
                .user(user)
                .build();
    }

    public OptimizedRouteJpa mapToJpa(OptimizedRoute optimizedRoute, RouteJpa routeJpa, User user) {
        var placesOverrides = writeObjectToString(mapper, optimizedRoute.route().places());
        var path = writeObjectToString(mapper, optimizedRoute.path());
        var weatherInfo = writeObjectToString(mapper, optimizedRoute.weatherInfo());
        return OptimizedRouteJpa.builder()
                .optimizationId(optimizedRoute.optimizationId())
                .route(routeJpa)
                .placesOverrides(placesOverrides)
                .path(path)
                .weatherInfo(weatherInfo)
                .user(user)
                .build();
    }

    private List<PlaceBoxed> readPlacesOverrides(String placesOverrides) {
        try {
            var rootNode = mapper.readTree(placesOverrides);
            return stream(rootNode.spliterator(), false)
                    .map(node -> PlaceBoxed.builder()
                            .name(node.get("name").asText())
                            .placeType(PlaceType.valueOf(node.get("placeType").asText()))
                            .position(
                                    new PointBoxed(node.get("position").get("lat").asDouble(),
                                            node.get("position").get("lng").asDouble()))
                            .build())
                    .toList();
        } catch (JsonProcessingException | NullPointerException e) {
            log.error("Cannot parse place overrides", e);
            throw new IllegalArgumentException("Cannot parse place overrides");
        }
    }

    private PathBoxed readPath(String path) {
        return readObjectFromString(mapper, path, PathBoxed.class);
    }

    private WeatherInfoBoxed readWeatherInfo(String weatherInfo) {
        return readObjectFromString(mapper, weatherInfo, WeatherInfoBoxed.class);
    }
}
