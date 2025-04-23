package io.jp.core.domain.weather;

import lombok.Builder;

@Builder
public record WindData(Double averageSpeed, String intensity, String description) {
}
