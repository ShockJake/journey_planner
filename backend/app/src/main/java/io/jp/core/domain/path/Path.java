package io.jp.core.domain.path;

import io.jp.core.domain.point.Point;
import lombok.Builder;

@Builder
public record Path(Point[] points, double time, double distance) {
}
