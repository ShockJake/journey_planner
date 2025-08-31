package io.jp.integration.provider.weather;

import io.jp.core.domain.weather.forecast.WeatherForecastBoxed;
import io.jp.mapper.forecast.WeatherForecastMapperNoOpt;
import io.jp.integration.provider.DataProvider;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MockedWeatherForecastProviderBoxed implements DataProvider<WeatherForecastBoxed> {
    private final WeatherForecastMapperNoOpt weatherForecastMapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public WeatherForecastBoxed getData(Map<String, Object> input) {
        var inputData = fileUtils.readFile("/data/mocked/OpenMeteoAPI_response.json");
        return weatherForecastMapper.map(inputData);
    }
}
