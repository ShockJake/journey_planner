package io.jp.core.domain.weather;

import lombok.Builder;

@Builder
public record GeneralData(Double meanTemperature, Double maxTemperature, Double minTemperature, Double maxWindSpeed) {
}
