package io.jp.core.organizer;

import io.jp.core.domain.place.Place;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static io.jp.core.placeresolver.NearestPlaceResolverPrimitiveArraysSplit.findClosestAdditionalPlace;
import static io.jp.mapper.point.PointMapper.mapTo2DArray;

@Slf4j
@Component
public class PlaceOrganizer {

    public void organize(List<Place> places) {
        var size = places.size();
        IntStream.range(0, size - 2)
                .forEach(i -> {
                    var currentPlace = places.get(i);
                    var xys = mapTo2DArray(places.subList(i + 1, size));

                    var closestPlaceIndex = findClosestAdditionalPlace(currentPlace, xys[0], xys[1]);
                    if (closestPlaceIndex == -1) return;
                    var closestPlace = places.get(closestPlaceIndex);
                    var nextPlace = places.get(i + 1);

                    if (!closestPlace.equals(nextPlace)) {
                        places.set(i + 1, closestPlace);
                        places.set(closestPlaceIndex, nextPlace);
                    }
                });
    }
}
