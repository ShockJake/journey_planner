package io.jp.core.optimizer.weather;

import io.jp.core.domain.weather.RainData;
import io.jp.core.domain.weather.RainIntensity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;

@Component
public class RainInfoProvider {
    public boolean shouldInclude(LocalDateTime startDateTime, Integer rainEndHour) {
        return startDateTime.withHour(rainEndHour).isAfter(startDateTime);
    }

    public List<RainData> getRainStartEnd(final List<Double> rainHourly) {
        var result = new ArrayList<RainData>();
        var currentRain = new RainData();
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

    private void markStartOfRain(RainData currentRain, int hour, Double rainAmount) {
        currentRain.setStartHour(hour);
        currentRain.setMaxHour(hour);
        currentRain.setMaxAmount(rainAmount);
    }

    private void updateMaxRainAmount(RainData currentRain, int hour, Double rainAmount) {
        if (currentRain.getMaxAmount() < rainAmount) {
            currentRain.setMaxHour(hour);
            currentRain.setMaxAmount(rainAmount);
        }
    }

    private void markEndOfRain(RainData currentRain, int hour, List<Double> rainHourly, List<RainData> result) {
        currentRain.setEndHour(hour);
        var rainIntensity = getRainIntensity(rainHourly.subList(currentRain.getStartHour(), currentRain.getEndHour()));
        currentRain.setRainIntensity(rainIntensity.name());
        currentRain.setRainDescription(rainIntensity.description());
        result.add(currentRain.copy());

        currentRain.setStartHour(null);
        currentRain.setEndHour(null);
        currentRain.setRainIntensity(null);
        currentRain.setRainDescription(null);
        currentRain.setMaxHour(null);
        currentRain.setMaxAmount(null);
    }

    private RainIntensity getRainIntensity(final List<Double> rainHourly) {
        var meanRain = rainHourly.stream()
                .mapToDouble(d -> d)
                .average()
                .orElseThrow(() -> new RuntimeException("Cannot calculate mean rain value"));
        return RainIntensity.fromValue(meanRain);
    }
}
