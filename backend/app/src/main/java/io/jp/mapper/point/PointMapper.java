package io.jp.mapper.point;

import io.jp.core.domain.place.Place;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.geometricmedian.Point;
import io.jp.database.entities.route.PlaceJpa;

import java.util.List;

import static java.util.stream.IntStream.range;

public class PointMapper {
    public static double[][] mapToPoints2DArray(List<Point> points) {
        var size = points.size();
        var xs = new double[size];
        var ys = new double[size];

        range(0, size).forEach(i -> {
            xs[i] = points.get(i).x;
            ys[i] = points.get(i).y;
        });

        return new double[][]{xs, ys};
    }

    public static double[][] mapTo2DArray(List<Place> places) {
        var size = places.size();
        double[] xs = new double[size];
        double[] ys = new double[size];
        range(0, size).forEach(i -> {
            var place = places.get(i);
            xs[i] = place.latitude();
            ys[i] = place.longitude();
        });
        return new double[][]{xs, ys};
    }

    public static List<Point> mapToPointsFromJpa(List<PlaceJpa> places) {
        return places.stream()
                .map(place -> new Point(place.getLatitude(), place.getLongitude()))
                .toList();
    }

    public static List<Point> mapToPoints(List<PlaceBoxed> places) {
        return places.stream()
                .map(place -> new Point(place.position().lat(), place.position().lng()))
                .toList();
    }
}
