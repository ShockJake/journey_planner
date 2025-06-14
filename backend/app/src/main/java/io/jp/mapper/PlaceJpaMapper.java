package io.jp.mapper;

import io.jp.core.domain.Place;
import io.jp.core.domain.Point;
import io.jp.database.entities.route.PlaceJpa;
import org.springframework.stereotype.Component;

@Component
public class PlaceJpaMapper {
    public Place mapFromJpa(PlaceJpa placeJpa) {
        return Place.builder()
                .name(placeJpa.getName())
                .placeType(placeJpa.getType())
                .position(Point.of(placeJpa.getLatitude(), placeJpa.getLongitude()))
                .build();
    }
}
