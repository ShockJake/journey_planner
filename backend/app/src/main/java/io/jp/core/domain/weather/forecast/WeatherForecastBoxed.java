package io.jp.core.domain.weather.forecast;


import lombok.Builder;

import java.util.List;

@Builder
public record WeatherForecastBoxed(Double latitude, Double longitude, List<Double> temperatureHourly, Double meanTemperature,
                                   List<Double> rainHourly, List<Integer> cloudCoverHourly, List<Double> windSpeedHourly,
                                   Double rainSum, Double maxWindSpeed, Double maxTemperature, Double minTemperature) {
}
