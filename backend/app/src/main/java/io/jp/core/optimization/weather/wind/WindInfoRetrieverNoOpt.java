package io.jp.core.optimization.weather.wind;

import io.jp.core.domain.weather.forecast.WeatherForecastBoxed;
import io.jp.core.domain.weather.wind.WindDataBoxed;
import io.jp.core.domain.weather.wind.WindIntensity;
import org.springframework.stereotype.Component;

@Component
public class WindInfoRetrieverNoOpt implements WindInfoRetriever<WindDataBoxed, WeatherForecastBoxed> {

    public WindDataBoxed getWindData(WeatherForecastBoxed forecast) {
        var averageWindSpeed = forecast.windSpeedHourly()
                .stream()
                .mapToDouble(el -> el)
                .average()
                .orElseThrow(() -> new RuntimeException("No wind speed data provided"));
        var intensity = WindIntensity.fromValue(averageWindSpeed);

        return WindDataBoxed.builder()
                .intensity(intensity.name())
                .averageSpeed(averageWindSpeed)
                .description(intensity.description())
                .build();
    }
}
