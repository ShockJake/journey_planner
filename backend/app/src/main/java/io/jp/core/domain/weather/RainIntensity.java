package io.jp.core.domain.weather;

public enum RainIntensity {
    LIGHT(2.5, "Gentle drizzle or soft rain"),
    MODERATE(7.6, "Steady and noticeable, may require umbrella"),
    HEAVY(50.0, "Quite intense rain, could lead to water accumulation and reduced visibility"),
    VERY_HEAVY(100.0, "Intense and potentially hazardous, could cause flash flooding"),
    EXTREME(1000.0, "Severe, can cause significant flooding and damage");

    private final Double threshold;
    private final String description;

    RainIntensity(Double threshold, String description) {
        this.threshold = threshold;
        this.description = description;
    }

    public Double threshold() {
        return threshold;
    }

    public String description() {
        return description;
    }

    public static RainIntensity fromValue(Double value) {
        if (value < LIGHT.threshold()) {
            return LIGHT;
        }
        if (value < MODERATE.threshold()) {
            return MODERATE;
        }
        if (value < HEAVY.threshold()) {
            return HEAVY;
        }
        if (value < VERY_HEAVY.threshold()) {
            return VERY_HEAVY;
        }
        return EXTREME;
    }
}
