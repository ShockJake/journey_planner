package io.jp.mapper;

import io.jp.core.domain.Point;
import io.jp.core.domain.Route;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteJpaMapper {
    private final PlaceJpaMapper placeJpaMapper;

    public Route mapFromJpa(RouteJpa routeJpa, List<PlaceJpa> jpaPlaces) {
        var places = jpaPlaces.stream().map(placeJpaMapper::mapFromJpa).toList();
        return Route.builder()
                .name(routeJpa.getName())
                .municipality(routeJpa.getMunicipality())
                .description(routeJpa.getDescription())
                .center(Point.of(routeJpa.getCenterLatitude(), routeJpa.getCenterLongitude()))
                .imageUrl(routeJpa.getImageUrl())
                .places(places)
                .build();
    }
}
