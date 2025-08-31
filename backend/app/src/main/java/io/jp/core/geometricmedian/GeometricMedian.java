package io.jp.core.geometricmedian;


import java.util.List;

public class GeometricMedian {
    private static final double TOLERANCE = 1e-10;
    private static final int MAX_ITERATIONS = 50;

    public static Point calculateGeometricMedian(List<Point> points) {
        var result = centroid(points);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            var nextPoint = calculateNextPoint(points, result);

            if (Math.hypot(nextPoint[0] - result.x, nextPoint[1] - result.y) < TOLERANCE) {
                break;
            }
            result.x = nextPoint[0];
            result.y = nextPoint[1];
        }
        return result;
    }

    private static Point centroid(List<Point> points) {
        Point result = points.stream().reduce(new Point(0, 0),
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
        int numberOfPoints = points.size();
        result.x /= numberOfPoints;
        result.y /= numberOfPoints;
        return result;
    }

    private static double[] calculateNextPoint(List<Point> points, Point result) {
        var results = points.stream().reduce(new Double[]{0.0, 0.0, 0.0},
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

        return new double[]{newX, newY};
    }
}
