package io.jp.core

import io.jp.core.domain.place.PlaceBoxed
import io.jp.core.domain.benchmark.PlacePrimitivePoint
import io.jp.core.domain.benchmark.PlacePrimitives
import io.jp.core.geometricmedian.Point
import io.jp.core.geometricmedian.PointBoxed

import static io.jp.database.entities.route.PlaceType.BUILDING
import static java.util.stream.IntStream.range

class DataGenerator {
    static Random random = new Random();

    static List<Point> generatePoints(int number, Point center) {
        return (0..number).toList().collect {
            def offset = random.nextDouble(0D, 1D)
            if (!random.nextBoolean()) {
                offset *= -1
            }
            return new Point(center.x + offset, center.y + offset)
        } as List<Point>
    }

    static List<PointBoxed> mapToBoxed(List<Point> points) {
        return points.collect { point ->
            new PointBoxed(Double.valueOf(point.x), Double.valueOf(point.y))
        } as List<PointBoxed>
    }

    static List<PlaceBoxed> generatePlaces(int number, PlaceBoxed start) {
        def startPoint = new Point(start.position().lat(), start.position().lng())
        return generatePoints(number, startPoint)
                .indexed()
                .collect {
                    def point = io.jp.core.domain.point.PointBoxed.of(it.value.x, it.value.y)
                    PlaceBoxed.builder()
                            .name("place_${it.key}")
                            .placeType(BUILDING)
                            .position(point)
                            .build()
                }
    }

    static List<PlacePrimitivePoint> mapToPrimitivePoint(List<PlaceBoxed> places) {
        return places.collect {
            def position = it.position()
            PlacePrimitivePoint.builder()
                    .name(it.name())
                    .placeType(it.placeType())
                    .position(new io.jp.core.domain.point.Point(position.lat(), position.lng()))
                    .build()
        }
    }

    static List<PlacePrimitives> mapToPrimitives(List<PlaceBoxed> places) {
        return places.collect {
            def position = it.position()
            PlacePrimitives.builder()
                    .name(it.name())
                    .placeType(it.placeType())
                    .lat(position.lat())
                    .lng(position.lng())
                    .build()
        }
    }

    static double[][] mapToPrimitiveArray(List<PlaceBoxed> places) {
        def size = places.size()
        var xs = new double[size]
        var ys = new double[size]

        range(0, size).forEach(i -> {
            xs[i] = places.get(i).position().lat()
            ys[i] = places.get(i).position().lng()
        });

        return new double[][]{xs, ys};
    }
}
