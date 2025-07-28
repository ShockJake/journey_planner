package io.jp.core.domain;

public enum RouteLongevity {
    SHORT, NORMAL, LONG;

    public int getNumberOfPlaces() {
        if (this.equals(SHORT)) {
            return 4;
        }
        if (this.equals(NORMAL)) {
            return 8;
        }
        if (this.equals(LONG)) {
            return 16;
        }
        return 4;
    }
}
