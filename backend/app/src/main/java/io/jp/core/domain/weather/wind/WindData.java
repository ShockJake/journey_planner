package io.jp.core.domain.weather.wind;

import lombok.Builder;

@Builder
public record WindData(double averageSpeed, String intensity, String description) {
}
