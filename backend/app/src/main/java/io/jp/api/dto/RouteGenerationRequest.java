package io.jp.api.dto;

import io.jp.core.domain.RouteLongevity;
import io.jp.database.entities.route.PlaceType;

import java.util.List;

public record RouteGenerationRequest(boolean saveToAccount, RouteLongevity routeLongevity, String municipality, List<PlaceType> places, String creationDateTime) {
}
