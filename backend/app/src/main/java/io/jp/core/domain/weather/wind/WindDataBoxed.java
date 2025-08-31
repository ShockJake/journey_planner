package io.jp.core.domain.weather.wind;

import lombok.Builder;

@Builder
public record WindDataBoxed(Double averageSpeed, String intensity, String description) {
}
