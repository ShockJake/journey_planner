package io.jp.core.domain.benchmark;

import io.jp.database.entities.route.PlaceType;
import lombok.Builder;

@Builder
public record PlacePrimitives(String name, PlaceType placeType, double lat, double lng) {
}
