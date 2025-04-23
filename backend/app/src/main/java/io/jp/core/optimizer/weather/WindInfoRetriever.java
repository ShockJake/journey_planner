package io.jp.core.optimizer.weather;

import io.jp.core.domain.weather.WeatherForecast;
import io.jp.core.domain.weather.WindData;
import io.jp.core.domain.weather.WindIntensity;
import org.springframework.stereotype.Component;

@Component
public class WindInfoRetriever {
    public WindData getWindData(WeatherForecast forecast) {
        var averageWindSpeed = forecast.windSpeedHourly()
                .stream()
                .mapToDouble(el -> el)
                .average()
                .orElseThrow(() -> new RuntimeException("No wind speed data provided"));
        var intensity = WindIntensity.fromValue(averageWindSpeed);
        return WindData.builder()
                .intensity(intensity.name())
                .averageSpeed(averageWindSpeed)
                .description(intensity.description())
                .build();
    }
}
