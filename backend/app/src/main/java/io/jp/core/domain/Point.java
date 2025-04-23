package io.jp.core.domain;

public record Point(Double lat, Double lng) {
    public static Point of(Double lat, Double lng) {
        return new Point(lat, lng);
    }
}
