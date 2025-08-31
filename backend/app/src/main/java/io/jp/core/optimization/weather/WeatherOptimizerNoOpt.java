package io.jp.core.optimization.weather;

import io.jp.core.domain.place.PlaceBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.core.domain.weather.forecast.WeatherForecastBoxed;
import io.jp.core.domain.weather.general.GeneralDataBoxed;
import io.jp.core.domain.weather.info.WeatherInfoBoxed;
import io.jp.core.domain.weather.rain.RainDataBoxed;
import io.jp.core.domain.weather.wind.WindDataBoxed;
import io.jp.core.optimization.weather.rain.RainInfoProvider;
import io.jp.core.optimization.weather.wind.WindInfoRetriever;
import io.jp.database.entities.route.PlaceType;
import io.jp.integration.provider.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.jp.core.placeresolver.NearestPlaceResolverPointBoxed.findClosestAdditionalPlace;
import static io.jp.database.entities.route.PlaceType.coveredPlaceTypes;
import static io.jp.utils.DateTimeUtils.getDate;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "no-opt")
public class WeatherOptimizerNoOpt implements WeatherOptimizer<RouteBoxed, RainDataBoxed, WeatherInfoBoxed> {
    private final RainInfoProvider<List<RainDataBoxed>, List<Double>> rainInfoProvider;
    private final WindInfoRetriever<WindDataBoxed, WeatherForecastBoxed> windInfoRetriever;
    private final DataProvider<WeatherForecastBoxed> weatherForecastDataProvider;
    private final List<PlaceType> coveredPlaceTypes = coveredPlaceTypes();

    @Override
    public WeatherInfoBoxed getWeatherInfo(RouteBoxed route, LocalDateTime startDateTime) {
        var routeCenter = route.center();
        var weatherForecast = weatherForecastDataProvider.getData(Map.of("latitude", routeCenter.lat(),
                "longitude", routeCenter.lng(), "date", getDate(startDateTime)));
        var rainInfo = rainInfoProvider.getRainStartEnd(weatherForecast.rainHourly())
                .stream()
                .filter(rainData -> rainInfoProvider.shouldInclude(startDateTime, rainData.getEndHour()))
                .toList();
        var windData = windInfoRetriever.getWindData(weatherForecast);
        var generalData = GeneralDataBoxed.builder()
                .maxWindSpeed(weatherForecast.maxWindSpeed())
                .maxTemperature(weatherForecast.maxTemperature())
                .minTemperature(weatherForecast.minTemperature())
                .meanTemperature(weatherForecast.meanTemperature())
                .build();
        optimizeRoute(route, rainInfo, startDateTime);
        return WeatherInfoBoxed.builder()
                .generalData(generalData)
                .rainData(rainInfo)
                .windData(windData)
                .build();
    }

    @Override
    public void optimizeRoute(RouteBoxed route, List<RainDataBoxed> rainData, LocalDateTime startDateTime) {
        if (rainData.isEmpty()) {
            return;
        }

        log.debug("Optimizing route {} with weather data", route.name());
        var startHour = startDateTime.getHour();
        var optimizedPlaces = new ArrayList<>(route.places());

        rainData.forEach(data -> processRainData(data, startHour, route, optimizedPlaces));
        route.updatePlaces(optimizedPlaces);
    }

    private void processRainData(RainDataBoxed rainData, int startHour, RouteBoxed route, List<PlaceBoxed> optimizedPlaces) {
        var additionalPlaces = new ArrayList<>(route.additionalPlaces());

        for (int i = startHour; i < startHour + optimizedPlaces.size(); i++) {
            var placeIndex = i - startHour;

            if (shouldLookForClosestPlace(rainData, i, optimizedPlaces.get(placeIndex).placeType())) {
                var place = optimizedPlaces.get(placeIndex);
                var closestPlace = findClosestAdditionalPlace(place, additionalPlaces);

                if (closestPlace == null) continue;

                log.debug("Found place {} to replace with {}", place.name(), closestPlace.name());

                optimizedPlaces.add(placeIndex, closestPlace);
                additionalPlaces.remove(closestPlace);
                break;
            }
        }
    }

    private boolean shouldLookForClosestPlace(RainDataBoxed data, int i, PlaceType placeType) {
        return isOverlappingWithRain(data, i) && !coveredPlaceTypes.contains(placeType);
    }

    private boolean isOverlappingWithRain(RainDataBoxed data, int hour) {
        return hour >= data.getStartHour() && hour <= data.getEndHour();
    }
}
