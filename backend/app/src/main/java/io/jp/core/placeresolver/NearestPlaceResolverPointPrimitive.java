package io.jp.core.placeresolver;

import io.jp.core.domain.benchmark.PlacePrimitivePoint;

import java.awt.geom.Point2D;
import java.util.List;

import static java.lang.Double.MAX_VALUE;

public class NearestPlaceResolverPointPrimitive {

    public static PlacePrimitivePoint findClosestPlace(PlacePrimitivePoint start, List<PlacePrimitivePoint> places) {
        var startPoint = new Point2D.Double(start.position().lat(), start.position().lng());
        PlacePrimitivePoint closest = null;
        double minDistance = MAX_VALUE;
        for (var place : places) {
            var point = place.position();
            double distance = startPoint.distance(point.lat(), point.lng());
            if (distance < minDistance) {
                minDistance = distance;
                closest = place;
            }
        }
        return closest;
    }
}
