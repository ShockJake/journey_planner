package io.jp.api;

import io.jp.core.domain.weather.WeatherForecast;
import io.jp.rest.provider.DataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestAPI {
    private final DataProvider<WeatherForecast> weatherForecastDataProvider;

    @GetMapping("/test")
    public ResponseEntity<WeatherForecast> getWeatherForecast() {
        return ResponseEntity.ok(weatherForecastDataProvider.getData(null));
    }
}
