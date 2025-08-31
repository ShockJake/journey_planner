package io.jp.core.placeresolver;

import io.jp.core.domain.place.Place;

import java.awt.geom.Point2D;

import static java.lang.Double.MAX_VALUE;



public class NearestPlaceResolverPrimitiveArraysSplit {

    public static int findClosestAdditionalPlace(Place start, double[] xs, double[] ys) {
        var startPoint = new Point2D.Double(start.latitude(), start.longitude());
        double minDistance = MAX_VALUE;
        int index = -1;

        double[] distances = new double[xs.length];
        for (int i = 0; i < xs.length; i++) {
            distances[i] = startPoint.distance(xs[i], ys[i]);
        }
        for (int i = 0; i < xs.length; i++) {
            if (distances[i] < minDistance) {
                minDistance = distances[i];
                index = i;
            }
        }
        return index;
    }
}
