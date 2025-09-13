package io.jp.core.organizer;

import io.jp.core.domain.place.Place;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

import static io.jp.core.placeresolver.NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace;
import static io.jp.mapper.point.PointMapper.mapTo2DArray;
import static org.apache.commons.lang3.ArrayUtils.subarray;

@Slf4j
@Component
public class PlaceOrganizer {

    public void organize(Place[] places) {
        var size = places.length;
        IntStream.range(0, size - 2)
                .forEach(i -> {
                    var currentPlace = places[i];
                    var xys = mapTo2DArray(subarray(places, i + 1, size));

                    var closestPlaceIndex = findClosestPlace(currentPlace, xys[0], xys[1]);
                    if (closestPlaceIndex == -1) return;
                    var closestPlace = places[closestPlaceIndex];
                    var nextPlace = places[i + 1];

                    if (!closestPlace.equals(nextPlace)) {
                        places[i + 1] = closestPlace;
                        places[closestPlaceIndex] = nextPlace;
                    }
                });
    }
}
