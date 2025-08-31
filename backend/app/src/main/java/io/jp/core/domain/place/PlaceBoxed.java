package io.jp.core.domain.place;

import io.jp.core.domain.point.PointBoxed;
import io.jp.database.entities.route.PlaceType;
import lombok.Builder;

@Builder
public record PlaceBoxed(String name, PlaceType placeType, PointBoxed position) {
}
