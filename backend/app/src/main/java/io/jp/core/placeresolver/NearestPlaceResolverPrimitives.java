package io.jp.core.placeresolver;

import io.jp.core.domain.benchmark.PlacePrimitives;

import java.awt.geom.Point2D;
import java.util.List;

import static java.lang.Double.MAX_VALUE;

public class NearestPlaceResolverPrimitives {

    public static PlacePrimitives findClosestAdditionalPlace(PlacePrimitives start, List<PlacePrimitives> places) {
        var startPoint = new Point2D.Double(start.lat(), start.lng());
        PlacePrimitives closest = null;
        double minDistance = MAX_VALUE;
        for (var place : places) {
            double distance = startPoint.distance(place.lat(), place.lng());
            if (distance < minDistance) {
                minDistance = distance;
                closest = place;
            }
        }
        return closest;
    }
}
