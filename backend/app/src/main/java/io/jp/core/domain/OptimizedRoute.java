package io.jp.core.domain;


import io.jp.core.domain.weather.WeatherInfo;
import lombok.Builder;

@Builder
public record OptimizedRoute(Route route, WeatherInfo weatherInfo) {
}
