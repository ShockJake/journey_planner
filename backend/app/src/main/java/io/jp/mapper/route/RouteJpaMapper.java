package io.jp.mapper.route;

import io.jp.core.domain.Place;
import io.jp.core.domain.Point;
import io.jp.core.domain.Route;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.mapper.place.PlaceJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static io.jp.database.entities.route.RouteType.CREATED;

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
                .municipality(routeJpa.getMunicipality().getName())
                .description(routeJpa.getDescription())
                .center(Point.of(routeJpa.getCenterLatitude(), routeJpa.getCenterLongitude()))
                .imageUrl(routeJpa.getImageUrl())
                .places(places)
                .additionalPlaces(additionalPlaces)
                .build();
    }

    public RouteJpa mapToJpa(Route route, Municipality municipality) {
        return RouteJpa.builder()
                .name(route.name())
                .description(route.description())
                .centerLatitude(route.center().lat())
                .centerLongitude(route.center().lng())
                .imageUrl(route.imageUrl())
                .routeType(CREATED)
                .municipality(municipality)
                .build();
    }
}
