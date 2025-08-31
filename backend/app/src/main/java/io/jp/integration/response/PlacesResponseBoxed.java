package io.jp.integration.response;

import io.jp.core.domain.place.PlaceBoxed;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PlacesResponseBoxed {
    private List<PlaceBoxed> places;
}
