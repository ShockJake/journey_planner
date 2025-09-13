package io.jp.core.optimization.weather.rain;

import io.jp.core.domain.weather.rain.RainData;
import io.jp.core.domain.weather.rain.RainIntensity;
import io.jp.core.optimization.weather.WeatherOptimizerOpt.HourlyRainData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.copyOfRange;
import static java.util.stream.IntStream.range;

@Slf4j
@Component
public class RainInfoProviderOpt implements RainInfoProvider<List<RainData>, HourlyRainData> {

    @Override
    public List<RainData> getRainStartEnd(final HourlyRainData rainHourlyData) {
        var rainHourly = rainHourlyData.data();
        var result = new ArrayList<RainData>();
        var currentRain = new RainData();
        currentRain.resetData();
        var rainLength = rainHourly.length;
        range(0, rainLength).forEach(hour -> {
            var rainAmount = rainHourly[hour];
            if (rainAmount != 0.0 && currentRain.getStartHour() == -1) {
                markStartOfRain(currentRain, hour, rainAmount);
            }
            if (rainAmount != 0.0 && currentRain.getStartHour() != -1) {
                updateMaxRainAmount(currentRain, hour, rainAmount);
            }
            if (rainAmount == 0.0 && currentRain.getStartHour() != -1) {
                markEndOfRain(currentRain, hour - 1, rainHourly, result);
            }
            if (hour == rainLength - 1 && currentRain.getEndHour() == -1 && currentRain.getStartHour() != -1) {
                markEndOfRain(currentRain, hour, rainHourly, result);
            }
        });
        return result;
    }

    private void markStartOfRain(RainData currentRain, int hour, double rainAmount) {
        currentRain.setStartHour(hour);
        currentRain.setMaxHour(hour);
        currentRain.setMaxAmount(rainAmount);
    }

    private void updateMaxRainAmount(RainData currentRain, int hour, double rainAmount) {
        if (currentRain.getMaxAmount() < rainAmount) {
            currentRain.setMaxHour(hour);
            currentRain.setMaxAmount(rainAmount);
        }
    }

    private void markEndOfRain(RainData currentRain, int hour, double[] rainHourly, List<RainData> result) {
        currentRain.setEndHour(hour);

        var rainIntensity = currentRain.getEndHour() == currentRain.getStartHour() ?
                RainIntensity.fromValue(currentRain.getMaxAmount()) :
                getRainIntensity(copyOfRange(rainHourly, currentRain.getStartHour(), currentRain.getEndHour()));

        currentRain.setRainIntensity(rainIntensity.name());
        currentRain.setRainDescription(rainIntensity.description());

        result.add(currentRain.copy());

        currentRain.resetData();
    }

    private RainIntensity getRainIntensity(final double[] rainHourly) {
        var meanRain = Arrays.stream(rainHourly)
                .average()
                .orElseThrow(() -> new RuntimeException("Cannot calculate mean rain value for: %s"
                        .formatted(Arrays.toString(rainHourly))));
        return RainIntensity.fromValue(meanRain);
    }
}
