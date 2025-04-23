package io.jp.core.domain.weather;

import lombok.Builder;

import java.util.List;

@Builder
public record WeatherInfo(GeneralData generalData, List<RainData> rainData, WindData windData) {
}
