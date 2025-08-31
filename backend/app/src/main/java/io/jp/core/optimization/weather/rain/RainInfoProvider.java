package io.jp.core.optimization.weather.rain;

import java.time.LocalDateTime;

public interface RainInfoProvider <RainDataType, RainHourlyData> {
    default boolean shouldInclude(LocalDateTime startDateTime, int rainEndHour) {
        return startDateTime.withHour(rainEndHour).isAfter(startDateTime);
    }

    RainDataType getRainStartEnd(RainHourlyData rainHourlyData);

}
