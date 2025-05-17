package io.jp.rest.provider;

import io.jp.core.domain.weather.WeatherForecast;
import io.jp.mapper.WeatherForecastMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static io.jp.rest.common.RestClientProvider.getRestClient;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
//@Component
@RequiredArgsConstructor
public class WeatherForecastProvider implements DataProvider<WeatherForecast> {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast?";
    private static final String FORECAST_PARAMETERS = "daily=temperature_2m_mean,rain_sum,wind_speed_10m_max,temperature_2m_max,temperature_2m_min&hourly=temperature_2m,rain,cloud_cover,wind_speed_10m&timezone=Europe/London&";

    private final WeatherForecastMapper weatherForecastMapper;
    private final RestClient restClient = getRestClient();

    @Override
    public WeatherForecast getData(Map<String, Object> input) {
        if (!containsAllNecessaryFields(input)) {
            throw new IllegalArgumentException("Input must contain date, latitude and longitude values");
        }
        var url = createFinalUrl((Double) input.get("latitude"), (Double) input.get("longitude"), (String) input.get("date"));
        log.info("Get weather forecast from {}", url);
        var response = restClient.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        var responseCode = response.getStatusCode();
        log.info("Weather forecast response is {}, for next params {}", responseCode, input);

        if (responseCode != HttpStatus.OK) {
            throw new HttpClientErrorException(responseCode);
        }
        return weatherForecastMapper.map(response.getBody());
    }

    private boolean containsAllNecessaryFields(Map<String, Object> input) {
        return input.containsKey("latitude") && input.containsKey("longitude")
                && input.containsKey("date");
    }

    private String createFinalUrl(Double latitude, Double longitude, String date) {
        var positionParameters = "latitude=%s&longitude=%s&".formatted(String.valueOf(latitude), String.valueOf(longitude));
        var dateParameters = "start_date=%s&end_date=%s".formatted(date, date);

        return BASE_URL + positionParameters + FORECAST_PARAMETERS + dateParameters;
    }
}
