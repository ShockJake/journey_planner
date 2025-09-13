package io.jp.core.placeresolver;

import io.jp.core.domain.place.PlaceBoxed;

import java.awt.geom.Point2D;
import java.util.List;

import static java.lang.Double.MAX_VALUE;

public class NearestPlaceResolverPointBase {

    public static PlaceBoxed findClosestPlace(PlaceBoxed start, List<PlaceBoxed> places) {
        PlaceBoxed closest = null;
        double minDistance = MAX_VALUE;
        for (var place : places) {
            var point = place.position();
            var startPoint2D = new Point2D.Double(start.position().lat(), start.position().lng());
            double distance = startPoint2D.distance(point.lat(), point.lng());
            if (distance < minDistance) {
                minDistance = distance;
                closest = place;
            }
        }
        return closest;
    }
}
