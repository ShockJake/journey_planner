package io.jp.core.domain.point;

public record Point(double lat, double lng) {
    public static Point of(double lat, double lng) {
        return new Point(lat, lng);
    }
}
