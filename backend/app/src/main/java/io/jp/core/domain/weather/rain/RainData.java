package io.jp.core.domain.weather.rain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RainData {
    private int startHour;
    private int endHour;
    private int maxHour;
    private double maxAmount;
    private String rainIntensity;
    private String rainDescription;

    public RainData copy() {
        return builder()
                .startHour(startHour)
                .endHour(endHour)
                .maxHour(maxHour)
                .rainIntensity(rainIntensity)
                .rainDescription(rainDescription)
                .maxAmount(maxAmount)
                .build();
    }

    public void resetData() {
        setStartHour(-1);
        setEndHour(-1);
        setRainIntensity(null);
        setRainDescription(null);
        setMaxHour(-1);
        setMaxAmount(-1);
    }
}
