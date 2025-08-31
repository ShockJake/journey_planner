package io.jp.mapper.place;

import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.point.PointBoxed;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RoutePlace;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class BoxedPlaceJpaMapper {
    public PlaceBoxed mapFromJpa(PlaceJpa placeJpa) {
        return PlaceBoxed.builder()
                .name(placeJpa.getName())
                .placeType(placeJpa.getType())
                .position(PointBoxed.of(placeJpa.getLatitude(), placeJpa.getLongitude()))
                .build();
    }

    public PlaceJpa mapToJpa(PlaceBoxed place, boolean isAdditional) {
        return PlaceJpa.builder()
                .name(place.name())
                .type(place.placeType())
                .latitude(place.position().lat())
                .longitude(place.position().lng())
                .isAdditional(isAdditional)
                .build();
    }

    public List<PlaceJpa> mapJpaFromRoutePlaces(List<RoutePlace> routePlaces) {
        return routePlaces.stream()
                .sorted(Comparator.comparing(RoutePlace::getPlaceIndex))
                .map(RoutePlace::getPlace)
                .toList();
    }
}
