package io.jp.core.domain.place;

import io.jp.database.entities.route.PlaceType;
import lombok.Builder;

@Builder
public record Place(String name, PlaceType placeType, double longitude, double latitude) {
}
