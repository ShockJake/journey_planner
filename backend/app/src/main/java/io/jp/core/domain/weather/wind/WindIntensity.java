package io.jp.core.domain.weather.wind;


public enum WindIntensity {
    CALM(5.0, "Smoke rises vertically, still conditions"),
    LIGHT_BREEZE(11.0, "Leaves rustle, weather vanes start to move"),
    GENTLE_BREEZE(19.0, "Leaves and small twigs move; flags flutter"),
    MODERATE_BREEZE(28.0, "Dust and loose paper lifted; small branches move"),
    FRESH_BREEZE(38.0, "Small trees sway, flags extend"),
    STRONG_BREEZE(49.0, "Large branches move, difficulty using umbrellas"),
    NEAR_GALE(61.0, "Trees in motion, walking against wind becomes challenging"),
    GALE(74.0, "Twigs break off trees; walking is difficult"),
    STRONG_GALE(88.0, "Slight structural damage possible; very difficult movement"),
    STORM(102.0, "Trees may be uprooted; damage likely"),
    VIOLENT_STORM(117.0, "Widespread damage, rare on land"),
    HURRICANE_FORCE(118.0, "Extreme damage; associated with tropical cyclones/hurricanes");

    private final Double threshold;
    private final String description;

    WindIntensity(Double threshold, String description) {
        this.threshold = threshold;
        this.description = description;
    }

    public Double threshold() {
        return threshold;
    }

    public String description() {
        return description;
    }

    public static WindIntensity fromValue(Double value) {
        if (value < CALM.threshold()) {
            return CALM;
        }
        if (value < LIGHT_BREEZE.threshold()) {
            return LIGHT_BREEZE;
        }
        if (value < GENTLE_BREEZE.threshold()) {
            return GENTLE_BREEZE;
        }
        if (value < MODERATE_BREEZE.threshold()) {
            return MODERATE_BREEZE;
        }
        if (value < FRESH_BREEZE.threshold()) {
            return FRESH_BREEZE;
        }
        if (value < NEAR_GALE.threshold()) {
            return NEAR_GALE;
        }
        if (value < GALE.threshold()) {
            return GALE;
        }
        if (value < STRONG_BREEZE.threshold()) {
            return STRONG_BREEZE;
        }
        if (value < STORM.threshold()) {
            return STORM;
        }
        if (value < VIOLENT_STORM.threshold()) {
            return VIOLENT_STORM;
        }
        return HURRICANE_FORCE;
    }
}
