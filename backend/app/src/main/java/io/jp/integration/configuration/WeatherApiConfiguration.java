package io.jp.integration.configuration;

import io.jp.core.domain.weather.WeatherForecast;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.weather.MockedWeatherForecastProvider;
import io.jp.integration.provider.weather.WeatherForecastProvider;
import io.jp.mapper.other.WeatherForecastMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherApiConfiguration {

    @ConditionalOnProperty(name = "integrations.api.weather.enabled", havingValue = "false")
    public DataProvider<WeatherForecast> mockedWeatherForecastProvider(WeatherForecastMapper weatherForecastMapper) {
        return new MockedWeatherForecastProvider(weatherForecastMapper);
    }

    @ConditionalOnProperty(name = "integrations.api.weather.enabled", havingValue = "true")
    public DataProvider<WeatherForecast> weatherForecastProvider(WeatherForecastMapper weatherForecastMapper) {
        return new WeatherForecastProvider(weatherForecastMapper);
    }
}
