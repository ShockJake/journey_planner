package io.jp.mapper.place;

import io.jp.core.domain.Place;
import io.jp.core.domain.Point;
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
                .position(Point.of(placeJpa.getLatitude(), placeJpa.getLongitude()))
                .build();
    }

    public PlaceJpa mapToJpa(Place place) {
        return PlaceJpa.builder()
                .name(place.name())
                .type(place.placeType())
                .latitude(place.position().lat())
                .longitude(place.position().lng())
                .build();
    }

    public List<PlaceJpa> mapJpaFromRoutePlaces(List<RoutePlace> routePlaces) {
        return routePlaces.stream()
                .sorted(Comparator.comparing(RoutePlace::getPlaceIndex))
                .map(RoutePlace::getPlace)
                .toList();
    }
}
