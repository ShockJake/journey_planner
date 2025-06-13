package io.jp.api.dto;

import io.jp.database.entities.route.PlaceType;
import lombok.Builder;

import java.util.List;

@Builder
public record RouteGenerationMetadata(List<String> municipalities, List<PlaceType> placeTypes) {
}
