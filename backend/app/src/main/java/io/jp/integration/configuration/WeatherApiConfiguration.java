package io.jp.integration.configuration;

import io.jp.core.domain.weather.forecast.WeatherForecast;
import io.jp.core.domain.weather.forecast.WeatherForecastBoxed;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.weather.MockedWeatherForecastProvider;
import io.jp.integration.provider.weather.MockedWeatherForecastProviderBoxed;
import io.jp.integration.provider.weather.WeatherForecastProviderNoOpt;
import io.jp.integration.provider.weather.WeatherForecastProviderOpt;
import io.jp.mapper.forecast.WeatherForecastMapperNoOpt;
import io.jp.mapper.forecast.WeatherForecastMapperOpt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WeatherApiConfiguration {

    @Bean
    @ConditionalOnExpression(
            "'${integrations.api.routing.enabled}'.equals('false') and '${service.implementation.route.optimization}'.equals('no-opt')"
    )
    public DataProvider<WeatherForecastBoxed> mockedWeatherForecastProviderBoxed(WeatherForecastMapperNoOpt weatherForecastMapper) {
        log.info("Using mocked implementation of boxed Weather Forecast Data Provider");
        return new MockedWeatherForecastProviderBoxed(weatherForecastMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "'${integrations.api.routing.enabled}'.equals('false') and '${service.implementation.route.optimization}'.equals('opt')"
    )
    public DataProvider<WeatherForecast> mockedWeatherForecastProvider(WeatherForecastMapperOpt weatherForecastMapper) {
        log.info("Using mocked implementation of Weather Forecast Data Provider");
        return new MockedWeatherForecastProvider(weatherForecastMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.routing.enabled} and '${service.implementation.route.optimization}'.equals('no-opt')"
    )
    public DataProvider<WeatherForecastBoxed> weatherForecastProviderBoxed(WeatherForecastMapperNoOpt weatherForecastMapper) {
        log.info("Using real implementation of boxed Weather Forecast Data Provider");
        return new WeatherForecastProviderNoOpt(weatherForecastMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.routing.enabled} and '${service.implementation.route.optimization}'.equals('opt')"
    )
    public DataProvider<WeatherForecast> weatherForecastProvider(WeatherForecastMapperOpt weatherForecastMapper) {
        log.info("Using real implementation of Weather Forecast Data Provider");
        return new WeatherForecastProviderOpt(weatherForecastMapper);
    }
}
