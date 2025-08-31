package io.jp.integration.provider.weather;

import io.jp.core.domain.weather.forecast.WeatherForecast;
import io.jp.integration.provider.DataProvider;
import io.jp.mapper.forecast.WeatherForecastMapperOpt;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MockedWeatherForecastProvider implements DataProvider<WeatherForecast> {
    private final WeatherForecastMapperOpt weatherForecastMapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public WeatherForecast getData(Map<String, Object> input) {
        var inputData = fileUtils.readFile("/data/mocked/OpenMeteoAPI_response.json");
        return weatherForecastMapper.map(inputData);
    }
}
