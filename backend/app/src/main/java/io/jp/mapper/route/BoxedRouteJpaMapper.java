package io.jp.mapper.route;

import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.database.entities.route.Municipality;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.mapper.place.BoxedPlaceJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static io.jp.database.entities.route.RouteType.CREATED;

@Component
@RequiredArgsConstructor
public class BoxedRouteJpaMapper {
    private final BoxedPlaceJpaMapper placeJpaMapper;

    public RouteBoxed mapFromJpa(RouteJpa routeJpa, List<PlaceJpa> jpaPlaces) {
        var places = new ArrayList<PlaceBoxed>();
        var additionalPlaces = new ArrayList<PlaceBoxed>();
        jpaPlaces.forEach(place -> {
            if (place.isAdditional()) {
                additionalPlaces.add(placeJpaMapper.mapFromJpa(place));
            } else {
                places.add(placeJpaMapper.mapFromJpa(place));
            }
        });
        return RouteBoxed.builder()
                .name(routeJpa.getName())
                .municipality(routeJpa.getMunicipality().getName())
                .description(routeJpa.getDescription())
                .center(PointBoxed.of(routeJpa.getCenterLatitude(), routeJpa.getCenterLongitude()))
                .imageUrl(routeJpa.getImageUrl())
                .places(places)
                .additionalPlaces(additionalPlaces)
                .build();
    }

    public RouteJpa mapToJpa(RouteBoxed route, Municipality municipality) {
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
