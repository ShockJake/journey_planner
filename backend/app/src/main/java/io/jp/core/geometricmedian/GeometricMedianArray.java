package io.jp.core.geometricmedian;


import java.util.stream.Stream;

public class GeometricMedianArray {
    private static final double TOLERANCE = 1e-10;
    private static final int MAX_ITERATIONS = 50;

    public static Point calculateGeometricMedian(Point[] points) {
        var result = centroid(points);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var newPoint = calculateNextPoint(points, result);

            if (Math.hypot(newPoint.x - result.x, newPoint.y - result.y) < TOLERANCE) {
                break;
            }
            result.x = newPoint.x;
            result.y = newPoint.y;
        }
        return result;
    }

    private static Point centroid(Point[] points) {
        Point result = Stream.of(points).reduce(new Point(0, 0),
                (partialResult, element) -> {
                    partialResult.x += element.x;
                    partialResult.y += element.y;
                    return partialResult;
                },
                (firstPartialResult, secondPartialResult) -> {
                    firstPartialResult.x += secondPartialResult.x;
                    firstPartialResult.y += secondPartialResult.y;
                    return firstPartialResult;
                });
        int numberOfPoints = points.length;
        result.x /= numberOfPoints;
        result.y /= numberOfPoints;
        return result;
    }

    private static Point calculateNextPoint(Point[] points, Point result) {
        var results = Stream.of(points).reduce(new Double[]{0.0, 0.0, 0.0},
                (partialResult, point) -> {
                    double dist = Math.hypot(result.x - point.x, result.y - point.y);
                    if (dist < 1e-10) return partialResult;
                    double weight = 1.0 / dist;
                    partialResult[0] += weight * point.x;
                    partialResult[1] += weight * point.y;
                    partialResult[2] += weight;
                    return partialResult;
                },
                (partialResult1, partialResult2) -> {
                    partialResult1[0] += partialResult2[0];
                    partialResult1[1] += partialResult2[2];
                    partialResult1[2] += partialResult2[3];
                    return partialResult1;
                });

        double newX = results[0] / results[2];
        double newY = results[1] / results[2];

        return new Point(newX, newY);
    }
}
