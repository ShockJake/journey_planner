package io.jp.api.dto;

import java.time.LocalDateTime;

public record RouteOptimizationRequest(String routeName, LocalDateTime startDateTime) {
}
