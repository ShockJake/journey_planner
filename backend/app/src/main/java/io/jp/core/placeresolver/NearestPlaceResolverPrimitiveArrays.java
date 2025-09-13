package io.jp.core.placeresolver;

import io.jp.core.domain.benchmark.PlacePrimitives;

import java.awt.geom.Point2D;

import static java.lang.Double.MAX_VALUE;

public class NearestPlaceResolverPrimitiveArrays {

    public static int findClosestPlace(PlacePrimitives start, double[] xs, double[] ys) {
        var startPoint = new Point2D.Double(start.lat(), start.lng());
        int index = 0;
        double minDistance = MAX_VALUE;
        for (int i = 0; i < xs.length; i++) {
            double distance = startPoint.distance(xs[i], ys[i]);
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }
        return index;
    }
}
