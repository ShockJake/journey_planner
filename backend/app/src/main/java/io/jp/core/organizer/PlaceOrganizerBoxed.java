package io.jp.core.organizer;

import io.jp.core.domain.place.PlaceBoxed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static io.jp.core.placeresolver.NearestPlaceResolverPointBoxed.findClosestAdditionalPlace;

@Slf4j
@Component
public class PlaceOrganizerBoxed {

    public void organize(List<PlaceBoxed> places) {
        var size = places.size();
        IntStream.range(0, size - 2)
                .forEach(i -> {
                    var currentPlace = places.get(i);
                    var closestPlace = findClosestAdditionalPlace(currentPlace, places.subList(i + 1, size));
                    var nextPlace = places.get(i + 1);

                    if (!closestPlace.equals(nextPlace)) {
                        var indexOfClosestPlace = places.indexOf(closestPlace);
                        places.set(i + 1, closestPlace);
                        places.set(indexOfClosestPlace, nextPlace);
                    }
                });
    }
}
