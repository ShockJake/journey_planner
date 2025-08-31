package io.jp.mapper.route;

import io.jp.core.domain.place.Place;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.mapper.place.BoxedPlaceJpaMapper;
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
                .centerLatitude(routeJpa.getCenterLatitude())
                .centerLongitude(routeJpa.getCenterLongitude())
                .imageUrl(routeJpa.getImageUrl())
                .places(places.toArray(new Place[0]))
                .additionalPlaces(additionalPlaces.toArray(new Place[0]))
                .build();
    }

    public RouteJpa mapToJpa(Route route, Municipality municipality) {
        return RouteJpa.builder()
                .name(route.name())
                .description(route.description())
                .centerLatitude(route.centerLatitude())
                .centerLongitude(route.centerLongitude())
                .imageUrl(route.imageUrl())
                .routeType(CREATED)
                .municipality(municipality)
                .build();
    }
}
