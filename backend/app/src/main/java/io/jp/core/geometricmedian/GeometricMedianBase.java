package io.jp.core.geometricmedian;


import java.util.List;

public class GeometricMedianBase {
    private static final double TOLERANCE = 1e-10;
    private static final int MAX_ITERATIONS = 50;

    public static Point calculateGeometricMedian(List<Point> points) {
        double x = 0, y = 0;
        for (Point p : points) {
            x += p.x;
            y += p.y;
        }
        x /= points.size();
        y /= points.size();

        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            double numX = 0, numY = 0, denom = 0;
            for (Point p : points) {
                double dist = Math.hypot(x - p.x, y - p.y);
                if (dist < 1e-10) continue;
                double weight = 1.0 / dist;
                numX += p.x * weight;
                numY += p.y * weight;
                denom += weight;
            }

            double newX = numX / denom;
            double newY = numY / denom;

            if (Math.hypot(newX - x, newY - y) < TOLERANCE)
                break;

            x = newX;
            y = newY;
        }

        return new Point(x, y);
    }

}
