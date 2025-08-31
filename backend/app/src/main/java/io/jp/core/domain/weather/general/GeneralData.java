package io.jp.core.domain.weather.general;

import lombok.Builder;

@Builder
public record GeneralData(double meanTemperature, double maxTemperature, double minTemperature, double maxWindSpeed) {
}
