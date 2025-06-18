package io.jp.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Path;
import io.jp.core.domain.Place;
import io.jp.core.domain.weather.WeatherInfo;
import io.jp.database.entities.route.OptimizedRouteJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.jp.utils.MappingUtils.readObjectFromString;
import static io.jp.utils.MappingUtils.writeObjectToString;

@Slf4j
@Component
@RequiredArgsConstructor
public class OptimizedRouteMapper {
    private final RouteJpaMapper routeJpaMapper;
    private final PlaceJpaMapper placeJpaMapper;
    private final ObjectMapper mapper;

    public OptimizedRoute mapFromJpa(OptimizedRouteJpa optimizedRouteJpa) {
        var jpaRoute = optimizedRouteJpa.getRoute();
        var jpaPlaces = placeJpaMapper.mapJpaFromRoutePlaces(jpaRoute.getPlaces());
        var route = routeJpaMapper.mapFromJpa(jpaRoute, jpaPlaces);

        var placesOverride = readPlacesOverrides(optimizedRouteJpa.getPlacesOverrides());
        route.updatePlaces(placesOverride);

        return OptimizedRoute.builder()
                .route(route)
                .path(readPath(optimizedRouteJpa.getPath()))
                .weatherInfo(readWeatherInfo(optimizedRouteJpa.getWeatherInfo()))
                .build();
    }

    public OptimizedRouteJpa mapToJpa(OptimizedRoute optimizedRoute, RouteJpa routeJpa, User user) {
        var placesOverrides = writeObjectToString(mapper, optimizedRoute.route().places());
        var path = writeObjectToString(mapper, optimizedRoute.path());
        var weatherInfo = writeObjectToString(mapper, optimizedRoute.weatherInfo());
        return OptimizedRouteJpa.builder()
                .route(routeJpa)
                .placesOverrides(placesOverrides)
                .path(path)
                .weatherInfo(weatherInfo)
                .user(user)
                .build();
    }

    @SuppressWarnings("unchecked")
    private List<Place> readPlacesOverrides(String placesOverrides) {
        return (List<Place>) readObjectFromString(mapper, placesOverrides, List.class);
    }

    private Path readPath(String path) {
        return readObjectFromString(mapper, path, Path.class);
    }

    private WeatherInfo readWeatherInfo(String weatherInfo) {
        return readObjectFromString(mapper, weatherInfo, WeatherInfo.class);
    }
}
