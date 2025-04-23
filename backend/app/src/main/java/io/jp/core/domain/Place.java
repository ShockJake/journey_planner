package io.jp.core.domain;

import io.jp.database.entities.route.PlaceType;
import lombok.Builder;

@Builder
public record Place(String name, PlaceType placeType, Point position) {
}
