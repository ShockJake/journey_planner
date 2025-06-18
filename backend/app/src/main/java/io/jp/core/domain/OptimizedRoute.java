package io.jp.core.domain;


import io.jp.core.domain.weather.WeatherInfo;
import lombok.Builder;

@Builder
public record OptimizedRoute(String optimizationId, Route route, Path path, WeatherInfo weatherInfo) {
}
