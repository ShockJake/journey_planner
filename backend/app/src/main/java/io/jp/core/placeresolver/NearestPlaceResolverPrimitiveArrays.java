package io.jp.core.placeresolver;

import io.jp.core.domain.benchmark.PlacePrimitives;

import java.awt.geom.Point2D;
import java.util.List;

import static java.lang.Double.MAX_VALUE;

public class NearestPlaceResolverPrimitiveArrays {

    public static double[] findClosestAdditionalPlace(PlacePrimitives start, double[] xs, double[] ys) {
        var startPoint = new Point2D.Double(start.lat(), start.lng());
        double[] result = new double[]{0.0, 0.0};
        double minDistance = MAX_VALUE;
        for (int i = 0; i < xs.length; i++) {
            double distance = startPoint.distance(xs[i], ys[i]);
            if (distance < minDistance) {
                minDistance = distance;
                result[0] = xs[i];
                result[1] = ys[i];
            }
        }
        return result;
    }
}
