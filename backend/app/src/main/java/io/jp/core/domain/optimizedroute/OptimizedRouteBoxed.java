package io.jp.core.domain.optimizedroute;


import io.jp.core.domain.path.PathBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.core.domain.weather.info.WeatherInfoBoxed;
import lombok.Builder;

@Builder
public record OptimizedRouteBoxed(String optimizationId, RouteBoxed route, PathBoxed path, WeatherInfoBoxed weatherInfo) {
}
