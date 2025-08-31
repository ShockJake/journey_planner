package io.jp.core.optimization.weather.rain;

import io.jp.core.domain.weather.rain.RainDataBoxed;
import io.jp.core.domain.weather.rain.RainIntensity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.IntStream.range;

@Slf4j
@Component
public class RainInfoProviderNoOpt implements RainInfoProvider<List<RainDataBoxed>, List<Double>> {

    @Override
    public List<RainDataBoxed> getRainStartEnd(final List<Double> rainHourly) {
        var result = new ArrayList<RainDataBoxed>();
        var currentRain = new RainDataBoxed();
        range(0, rainHourly.size()).forEach(hour -> {
            var rainAmount = rainHourly.get(hour);
            if (!rainAmount.equals(0.0) && currentRain.getStartHour() == null) {
                markStartOfRain(currentRain, hour, rainAmount);
            }
            if (!rainAmount.equals(0.0) && currentRain.getStartHour() != null) {
                updateMaxRainAmount(currentRain, hour, rainAmount);
            }
            if (rainAmount.equals(0.0) && currentRain.getStartHour() != null) {
                markEndOfRain(currentRain, hour - 1, rainHourly, result);
            }
            if (hour == rainHourly.size() - 1 && currentRain.getEndHour() == null && currentRain.getStartHour() != null) {
                markEndOfRain(currentRain, hour, rainHourly, result);
            }
        });
        return result;
    }

    private void markStartOfRain(RainDataBoxed currentRain, int hour, Double rainAmount) {
        currentRain.setStartHour(hour);
        currentRain.setMaxHour(hour);
        currentRain.setMaxAmount(rainAmount);
    }

    private void updateMaxRainAmount(RainDataBoxed currentRain, int hour, Double rainAmount) {
        if (currentRain.getMaxAmount() < rainAmount) {
            currentRain.setMaxHour(hour);
            currentRain.setMaxAmount(rainAmount);
        }
    }

    private void markEndOfRain(RainDataBoxed currentRain, int hour, List<Double> rainHourly, List<RainDataBoxed> result) {
        currentRain.setEndHour(hour);

        var rainIntensity = Objects.equals(currentRain.getEndHour(), currentRain.getStartHour()) ?
                RainIntensity.fromValue(currentRain.getMaxAmount()) :
                getRainIntensity(rainHourly.subList(currentRain.getStartHour(), currentRain.getEndHour()));

        currentRain.setRainIntensity(rainIntensity.name());
        currentRain.setRainDescription(rainIntensity.description());

        result.add(currentRain.copy());

        currentRain.resetData();
    }

    private RainIntensity getRainIntensity(final List<Double> rainHourly) {
        var meanRain = rainHourly.stream()
                .mapToDouble(d -> d)
                .average()
                .orElseThrow(() ->
                        new RuntimeException("Cannot calculate mean rain value for: %s".formatted(rainHourly)));
        return RainIntensity.fromValue(meanRain);
    }
}
