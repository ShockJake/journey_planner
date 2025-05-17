package io.jp.core.optimizer.weather;

import io.jp.core.domain.Route;
import io.jp.core.domain.weather.GeneralData;
import io.jp.core.domain.weather.WeatherForecast;
import io.jp.core.domain.weather.WeatherInfo;
import io.jp.rest.provider.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

import static io.jp.utils.DateTimeUtils.getDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherOptimizer {
    private final RainInfoProvider rainInfoProvider;
    private final WindInfoRetriever windInfoRetriever;
    private final DataProvider<WeatherForecast> weatherForecastDataProvider;

    public WeatherInfo getWeatherInfo(final Route route, LocalDateTime startDateTime) {
        var routeCenter = route.center();
        var weatherForecast = weatherForecastDataProvider.getData(Map.of("latitude", routeCenter.lat(),
                "longitude", routeCenter.lng(), "date", getDate(startDateTime)));
        var rainInfo = rainInfoProvider.getRainStartEnd(weatherForecast.rainHourly())
                .stream()
                .filter(rainData -> rainInfoProvider.shouldInclude(startDateTime, rainData.getEndHour()))
                .toList();
        var windData = windInfoRetriever.getWindData(weatherForecast);
        var generalData = GeneralData.builder()
                .maxWindSpeed(weatherForecast.maxWindSpeed())
                .maxTemperature(weatherForecast.maxTemperature())
                .minTemperature(weatherForecast.minTemperature())
                .meanTemperature(weatherForecast.meanTemperature())
                .build();

        return WeatherInfo.builder()
                .generalData(generalData)
                .rainData(rainInfo)
                .windData(windData)
                .build();
    }
}
