package io.jp.core.domain.path;

import io.jp.core.domain.point.PointBoxed;
import lombok.Builder;
import java.util.List;

@Builder
public record PathBoxed(List<PointBoxed> points, double time, double distance) {
}
