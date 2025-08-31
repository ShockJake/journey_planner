package io.jp.core.domain.weather.general;

import lombok.Builder;

@Builder
public record GeneralDataBoxed(Double meanTemperature, Double maxTemperature, Double minTemperature, Double maxWindSpeed) {
}
