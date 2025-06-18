package io.jp.core;

import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Path;
import io.jp.core.domain.Route;
import io.jp.core.optimizer.weather.WeatherOptimizer;
import io.jp.integration.provider.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.UUID.randomUUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteOptimizer {
    private final WeatherOptimizer weatherOptimizer;
    private final DataProvider<Path> routingDataProvider;

    public OptimizedRoute optimizeRoute(Route route, LocalDateTime startDateTime) {
        log.info("Optimizing route: '{}', with start date time - {}", route.name(), startDateTime);
        var weatherInfo = weatherOptimizer.getWeatherInfo(route, startDateTime);
        var path = routingDataProvider.getData(Map.of("places", route.places()));
        var optimizationId = randomUUID().toString();
        return OptimizedRoute.builder()
                .optimizationId(optimizationId)
                .route(route)
                .weatherInfo(weatherInfo)
                .path(path)
                .build();
    }
}
