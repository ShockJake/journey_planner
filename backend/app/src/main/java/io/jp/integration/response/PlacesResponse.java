package io.jp.integration.response;

import io.jp.core.domain.Place;
import io.jp.database.entities.route.PlaceJpa;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class PlacesResponse {
    private List<Place> places;
}
