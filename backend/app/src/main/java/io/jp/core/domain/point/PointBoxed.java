package io.jp.core.domain.point;

public record PointBoxed(Double lat, Double lng) {
    public static PointBoxed of(Double lat, Double lng) {
        return new PointBoxed(lat, lng);
    }
}
