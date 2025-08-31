package io.jp.core.placeresolver;

import io.jp.core.domain.place.PlaceBoxed;

import java.awt.geom.Point2D;
import java.util.List;

import static java.lang.Double.MAX_VALUE;

public class NearestPlaceResolverPointBoxed {

    public static PlaceBoxed findClosestAdditionalPlace(PlaceBoxed start, List<PlaceBoxed> places) {
        var startPoint = new Point2D.Double(start.position().lat(), start.position().lng());
        PlaceBoxed closestPlace = null;
        double minDistance = MAX_VALUE;

        for (var place : places) {
            var point = place.position();
            double distance = startPoint.distance(point.lat(), point.lng());
            if (distance < minDistance) {
                minDistance = distance;
                closestPlace = place;
            }
        }

        return closestPlace;
    }
}
