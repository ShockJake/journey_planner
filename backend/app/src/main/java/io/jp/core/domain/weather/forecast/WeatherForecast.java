package io.jp.core.domain.weather.forecast;


import lombok.Builder;

import java.util.List;

@Builder
public record WeatherForecast(double latitude, double longitude, double[] temperatureHourly, double meanTemperature,
                              double[] rainHourly, int[] cloudCoverHourly, double[] windSpeedHourly,
                              double rainSum, double maxWindSpeed, double maxTemperature, double minTemperature) {
}
