package io.jp.mapper.place;

import io.jp.core.domain.place.Place;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RoutePlace;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PlaceJpaMapper {
    public Place mapFromJpa(PlaceJpa placeJpa) {
        return Place.builder()
                .name(placeJpa.getName())
                .placeType(placeJpa.getType())
                .latitude(placeJpa.getLatitude())
                .longitude(placeJpa.getLongitude())
                .build();
    }

    public PlaceJpa mapToJpa(Place place) {
        return PlaceJpa.builder()
                .name(place.name())
                .type(place.placeType())
                .latitude(place.latitude())
                .longitude(place.longitude())
                .build();
    }

    public List<PlaceJpa> mapJpaFromRoutePlaces(List<RoutePlace> routePlaces) {
        return routePlaces.stream()
                .sorted(Comparator.comparing(RoutePlace::getPlaceIndex))
                .map(RoutePlace::getPlace)
                .toList();
    }
}
