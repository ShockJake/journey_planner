package io.jp.utils;

import io.jp.core.GeometricMedian;
import io.jp.core.domain.Place;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.PlaceType;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {
    public static List<GeometricMedian.Point> mapToPointsFromJpa(List<PlaceJpa> places) {
        return places.stream()
                .map(place -> new GeometricMedian.Point(place.getLatitude(), place.getLongitude()))
                .toList();
    }

    public static List<GeometricMedian.Point> mapToPoints(List<Place> places) {
        return places.stream()
                .map(place -> new GeometricMedian.Point(place.position().lat(), place.position().lng()))
                .toList();
    }

    public static String mapDataToDescription(String municipality, List<PlaceType> placeTypes) {
        var placesDescription = placeTypes.stream()
                .map(PlaceType::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(","));
        return "Discover %s by visiting %s".formatted(municipality, placesDescription);
    }
}
