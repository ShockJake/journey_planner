package io.jp.integration.configuration;

import io.jp.core.domain.weather.WeatherForecast;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.weather.MockedWeatherForecastProvider;
import io.jp.integration.provider.weather.WeatherForecastProvider;
import io.jp.mapper.other.WeatherForecastMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WeatherApiConfiguration {

    @Bean
    @ConditionalOnProperty(name = "integrations.api.weather.enabled", havingValue = "false")
    public DataProvider<WeatherForecast> mockedWeatherForecastProvider(WeatherForecastMapper weatherForecastMapper) {
        log.info("Using mocked implementation of Weather Forecast Data Provider");
        return new MockedWeatherForecastProvider(weatherForecastMapper);
    }

    @Bean
    @ConditionalOnProperty(name = "integrations.api.weather.enabled", havingValue = "true")
    public DataProvider<WeatherForecast> weatherForecastProvider(WeatherForecastMapper weatherForecastMapper) {
        log.info("Using real implementation of Weather Forecast Data Provider");
        return new WeatherForecastProvider(weatherForecastMapper);
    }
}
