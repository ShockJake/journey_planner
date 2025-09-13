package io.jp.mapper.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.weather.forecast.WeatherForecast;
import io.jp.core.domain.weather.forecast.WeatherForecastBoxed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;
import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherForecastMapperOpt {
    private final ObjectMapper objectMapper;

    public WeatherForecast map(String input) {
        try {
            var rootNode = objectMapper.readTree(input);
            var latitude = rootNode.get("latitude").asDouble();
            var longitude = rootNode.get("longitude").asDouble();
            var dailyInfoNode = rootNode.get("daily");
            var hourlyInfoNode = rootNode.get("hourly");

            return WeatherForecast.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .maxTemperature(dailyInfoNode.get("temperature_2m_max").get(0).asDouble())
                    .minTemperature(dailyInfoNode.get("temperature_2m_min").get(0).asDouble())
                    .maxWindSpeed(dailyInfoNode.get("wind_speed_10m_max").get(0).asDouble())
                    .rainSum(dailyInfoNode.get("rain_sum").get(0).asDouble())
                    .meanTemperature(dailyInfoNode.get("temperature_2m_mean").get(0).asDouble())
                    .rainHourly(getHourlyDoubleData(hourlyInfoNode, "rain"))
                    .cloudCoverHourly(getHourlyIntData(hourlyInfoNode, "cloud_cover"))
                    .temperatureHourly(getHourlyDoubleData(hourlyInfoNode, "temperature_2m"))
                    .windSpeedHourly(getHourlyDoubleData(hourlyInfoNode, "wind_speed_10m"))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Cannot map Weather Forecast: {}", e.getMessage());
            return WeatherForecast.builder().build();
        }
    }

    private double[] getHourlyDoubleData(JsonNode source, String arrayField) {
        var dataArray = source.get(arrayField);
        if (!dataArray.isArray()) {
            throw new RuntimeException(String.format("%s is not an array", arrayField));
        }
        var size = dataArray.size();
        var result = new double[size];
        range(0, size).forEach(i -> result[i] = dataArray.get(i).asDouble());

        return result;
    }

    private int[] getHourlyIntData(JsonNode source, String arrayField) {
        var dataArray = source.get(arrayField);
        if (!dataArray.isArray()) {
            throw new RuntimeException(String.format("%s is not an array", arrayField));
        }
        var size = dataArray.size();
        var result = new int[size];
        range(0, size).forEach(i -> result[i] = dataArray.get(i).asInt());

        return result;
    }
}
