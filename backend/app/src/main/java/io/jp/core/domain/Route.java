package io.jp.core.domain;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record Route(String name, String municipality, String description, String imageUrl, Point center, List<Place> places, List<Place> additionalPlaces) {
    public Route copy() {
        return Route.builder()
                .name(name)
                .municipality(municipality)
                .description(description)
                .imageUrl(imageUrl)
                .center(center)
                .places(new ArrayList<>(places))
                .additionalPlaces(new ArrayList<>(additionalPlaces))
                .build();
    }

    public void updatePlaces(List<Place> places) {
        this.places.clear();
        this.places.addAll(places);
    }
}
