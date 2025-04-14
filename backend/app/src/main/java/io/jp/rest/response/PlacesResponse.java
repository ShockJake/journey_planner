package io.jp.rest.response;

import io.jp.database.entities.Place;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class PlacesResponse {
    private List<Place> places;
}
