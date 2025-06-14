package io.jp.integration.provider.weather;

import io.jp.core.domain.weather.WeatherForecast;
import io.jp.mapper.WeatherForecastMapper;
import io.jp.integration.provider.DataProvider;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MockedWeatherForecastProvider implements DataProvider<WeatherForecast> {
    private final WeatherForecastMapper weatherForecastMapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public WeatherForecast getData(Map<String, Object> input) {
        var inputData = fileUtils.readFile("/data/mocked/OpenMeteoAPI_response.json");
        return weatherForecastMapper.map(inputData);
    }
}
