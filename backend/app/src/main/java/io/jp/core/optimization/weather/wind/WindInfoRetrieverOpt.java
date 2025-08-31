package io.jp.core.optimization.weather.wind;

import io.jp.core.domain.weather.forecast.WeatherForecast;
import io.jp.core.domain.weather.forecast.WeatherForecastBoxed;
import io.jp.core.domain.weather.wind.WindData;
import io.jp.core.domain.weather.wind.WindDataBoxed;
import io.jp.core.domain.weather.wind.WindIntensity;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WindInfoRetrieverOpt implements WindInfoRetriever<WindData, WeatherForecast> {
    private static final VectorSpecies<Double> species = DoubleVector.SPECIES_PREFERRED;

    public WindData getWindData(WeatherForecast forecast) {
        var windSpeedHourly = forecast.windSpeedHourly();
        if (windSpeedHourly.length == 0) {
            throw new RuntimeException("No wind speed data provided");
        }
        var vector = DoubleVector.fromArray(species, windSpeedHourly, 0);
        var averageWindSpeed = vector.reduceLanes(VectorOperators.ADD) / windSpeedHourly.length;
        var intensity = WindIntensity.fromValue(averageWindSpeed);

        return WindData.builder()
                .intensity(intensity.name())
                .averageSpeed(averageWindSpeed)
                .description(intensity.description())
                .build();
    }
}
