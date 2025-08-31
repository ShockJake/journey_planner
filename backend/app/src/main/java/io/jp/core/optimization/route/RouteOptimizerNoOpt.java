package io.jp.core.optimization.route;

import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.core.domain.path.PathBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.core.domain.weather.info.WeatherInfoBoxed;
import io.jp.core.domain.weather.rain.RainDataBoxed;
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
@ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "no-opt")
public class RouteOptimizerNoOpt implements RouteOptimizer<RouteBoxed, OptimizedRouteBoxed> {
    private final WeatherOptimizer<RouteBoxed, RainDataBoxed, WeatherInfoBoxed> weatherOptimizer;
    private final DataProvider<PathBoxed> routingDataProvider;

    @Override
    public OptimizedRouteBoxed optimizeRoute(RouteBoxed route, LocalDateTime startDateTime) {
        log.info("Optimizing route: '{}', with start date time - {}", route.name(), startDateTime);
        var weatherInfo = weatherOptimizer.getWeatherInfo(route, startDateTime);
        var path = routingDataProvider.getData(Map.of("places", route.places()));
        var optimizationId = randomUUID().toString();
        return OptimizedRouteBoxed.builder()
                .optimizationId(optimizationId)
                .route(route)
                .weatherInfo(weatherInfo)
                .path(path)
                .build();
    }
}
