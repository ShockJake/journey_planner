package io.jp.core.domain.benchmark;

import io.jp.core.domain.point.Point;
import io.jp.database.entities.route.PlaceType;
import lombok.Builder;

@Builder
public record PlacePrimitivePoint(String name, PlaceType placeType, Point position) {
}
