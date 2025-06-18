package io.jp.core.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RainData {
    private Integer startHour;
    private Integer endHour;
    private Integer maxHour;
    private Double maxAmount;
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
        setStartHour(null);
        setEndHour(null);
        setRainIntensity(null);
        setRainDescription(null);
        setMaxHour(null);
        setMaxAmount(null);
    }
}
