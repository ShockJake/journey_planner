package io.jp.core.domain;

import lombok.Builder;
import java.util.List;

@Builder
public record Path(List<Point> points, double time, double distance) {
}
