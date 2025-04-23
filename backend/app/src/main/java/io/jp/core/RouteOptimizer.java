package io.jp.core;

import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Route;
import io.jp.core.optimizer.weather.WeatherOptimizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteOptimizer {
    private final WeatherOptimizer weatherOptimizer;

    public OptimizedRoute optimizeRoute(Route route, LocalDateTime startDateTime) {
        log.info("Optimizing route: '{}', with start date time - {}", route.name(), startDateTime);
        return OptimizedRoute.builder()
                .route(route)
                .weatherInfo(weatherOptimizer.getWeatherInfo(route, startDateTime))
                .build();
    }
}
