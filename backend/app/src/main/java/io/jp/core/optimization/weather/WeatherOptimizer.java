package io.jp.core.optimization.weather;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherOptimizer<RouteDataType, RainDataType, WeatherInfoType> {
    WeatherInfoType getWeatherInfo(RouteDataType data, LocalDateTime time);
    void optimizeRoute(RouteDataType routeData, List<RainDataType> rainData, LocalDateTime startDateTime);
}
