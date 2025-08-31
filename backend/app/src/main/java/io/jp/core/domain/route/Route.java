package io.jp.core.domain.route;

import io.jp.core.domain.benchmark.PlacePrimitives;
import io.jp.core.domain.place.Place;
import lombok.Builder;

import static java.util.stream.IntStream.range;

@Builder
public record Route(String name, String municipality, String description, String imageUrl, double centerLatitude,
                    double centerLongitude, Place[] places, Place[] additionalPlaces) {
    public Route copy() {
        return Route.builder()
                .name(name)
                .municipality(municipality)
                .description(description)
                .imageUrl(imageUrl)
                .centerLatitude(centerLatitude)
                .centerLongitude(centerLongitude)
                .places(places.clone())
                .additionalPlaces(additionalPlaces.clone())
                .build();
    }

    public void updatePlaces(Place[] newPlaces) {
        range(0, places.length).forEach(i -> places[i] = newPlaces[i]);
    }
}
