package io.jp.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jp.core.domain.weather.WeatherForecast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.StreamSupport.stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherForecastMapper {
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
                    .rainHourly(getHourlyData(hourlyInfoNode, "rain", JsonNode::asDouble))
                    .cloudCoverHourly(getHourlyData(hourlyInfoNode, "cloud_cover", JsonNode::asInt))
                    .temperatureHourly(getHourlyData(hourlyInfoNode, "temperature_2m", JsonNode::asDouble))
                    .windSpeedHourly(getHourlyData(hourlyInfoNode, "wind_speed_10m", JsonNode::asDouble))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Cannot map Weather Forecast: {}", e.getMessage());
            return WeatherForecast.builder().build();
        }
    }

    private <T> List<T> getHourlyData(JsonNode source, String arrayField, Function<JsonNode, T> mapper) {
        var dataArray = source.get(arrayField);
        if (!dataArray.isArray()) {
            throw new RuntimeException(String.format("%s is not an array", arrayField));
        }
        return stream(dataArray.spliterator(), false)
                .map(mapper)
                .toList();
    }
}
