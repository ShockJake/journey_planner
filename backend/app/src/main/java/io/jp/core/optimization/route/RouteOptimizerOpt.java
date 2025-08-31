package io.jp.core.optimization.route;

import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.path.Path;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.weather.info.WeatherInfo;
import io.jp.core.domain.weather.rain.RainData;
import io.jp.core.optimization.weather.WeatherOptimizer;
import io.jp.integration.provider.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.UUID.randomUUID;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "opt")
public class RouteOptimizerOpt implements RouteOptimizer<Route, OptimizedRoute> {
    private final WeatherOptimizer<Route, RainData, WeatherInfo> weatherOptimizer;
    private final DataProvider<Path> routingDataProvider;

    @Override
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
