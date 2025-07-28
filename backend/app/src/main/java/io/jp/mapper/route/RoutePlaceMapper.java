package io.jp.mapper;

import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.route.RoutePlace;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Component
public class RoutePlaceMapper {

    public RoutePlace mapToRoutePlace(RouteJpa route, PlaceJpa place, int index) {
        return RoutePlace.builder()
                .placeIndex(index)
                .place(place)
                .route(route)
                .build();
    }

    public List<PlaceJpa> mapToPlaceList(List<RoutePlace> routePlaces) {
        return routePlaces.stream()
                .sorted(comparing(RoutePlace::getPlaceIndex))
                .map(RoutePlace::getPlace)
                .collect(Collectors.toList());
    }
}
