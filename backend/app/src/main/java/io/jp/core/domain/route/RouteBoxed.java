package io.jp.core.domain.route;

import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record RouteBoxed(String name, String municipality, String description, String imageUrl, PointBoxed center,
                         List<PlaceBoxed> places, List<PlaceBoxed> additionalPlaces) {
    public RouteBoxed copy() {
        return RouteBoxed.builder()
                .name(name)
                .municipality(municipality)
                .description(description)
                .imageUrl(imageUrl)
                .center(center)
                .places(new ArrayList<>(places))
                .additionalPlaces(new ArrayList<>(additionalPlaces))
                .build();
    }

    public void updatePlaces(List<PlaceBoxed> places) {
        this.places.clear();
        this.places.addAll(places);
    }
}
