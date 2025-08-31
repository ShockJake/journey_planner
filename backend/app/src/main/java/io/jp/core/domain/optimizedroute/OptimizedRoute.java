package io.jp.core.domain.optimizedroute;


import io.jp.core.domain.path.Path;
import io.jp.core.domain.path.PathBoxed;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.weather.info.WeatherInfo;
import io.jp.core.domain.weather.info.WeatherInfoBoxed;
import lombok.Builder;

@Builder
public record OptimizedRoute(String optimizationId, Route route, Path path, WeatherInfo weatherInfo) {
}
