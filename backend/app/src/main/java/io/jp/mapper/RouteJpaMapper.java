package io.jp.mapper;

import io.jp.core.domain.Place;
import io.jp.core.domain.Point;
import io.jp.core.domain.Route;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteJpaMapper {
    private final PlaceJpaMapper placeJpaMapper;

    public Route mapFromJpa(RouteJpa routeJpa, List<PlaceJpa> jpaPlaces) {
        var places = new ArrayList<Place>();
        var additionalPlaces = new ArrayList<Place>();
        jpaPlaces.forEach(place -> {
            if (place.isAdditional()) {
                additionalPlaces.add(placeJpaMapper.mapFromJpa(place));
            } else {
                places.add(placeJpaMapper.mapFromJpa(place));
            }
        });
        return Route.builder()
                .name(routeJpa.getName())
                .municipality(routeJpa.getMunicipality())
                .description(routeJpa.getDescription())
                .center(Point.of(routeJpa.getCenterLatitude(), routeJpa.getCenterLongitude()))
                .imageUrl(routeJpa.getImageUrl())
                .places(places)
                .additionalPlaces(additionalPlaces)
                .build();
    }
}
