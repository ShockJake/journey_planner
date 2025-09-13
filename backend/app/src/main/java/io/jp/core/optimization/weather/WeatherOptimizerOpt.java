package io.jp.core.optimization.weather;

import io.jp.core.domain.place.Place;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.weather.forecast.WeatherForecast;
import io.jp.core.domain.weather.general.GeneralData;
import io.jp.core.domain.weather.info.WeatherInfo;
import io.jp.core.domain.weather.rain.RainData;
import io.jp.core.domain.weather.wind.WindData;
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

import static io.jp.core.placeresolver.NearestPlaceResolverPrimitiveArraysSplit.findClosestPlace;
import static io.jp.database.entities.route.PlaceType.BUILDING;
import static io.jp.database.entities.route.PlaceType.CHURCH;
import static io.jp.database.entities.route.PlaceType.COFFEE;
import static io.jp.database.entities.route.PlaceType.MUSEUM;
import static io.jp.database.entities.route.PlaceType.STORE;
import static io.jp.mapper.point.PointMapper.mapTo2DArray;
import static io.jp.utils.DateTimeUtils.getDate;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "opt")
public class WeatherOptimizerOpt implements WeatherOptimizer<Route, RainData, WeatherInfo> {
    private final RainInfoProvider<List<RainData>, HourlyRainData> rainInfoProvider;
    private final WindInfoRetriever<WindData, WeatherForecast> windInfoRetriever;
    private final DataProvider<WeatherForecast> weatherForecastDataProvider;
    private final List<PlaceType> coveredPlaceTypes = List.of(CHURCH, BUILDING, MUSEUM, STORE, COFFEE);

    public record HourlyRainData(double[] data) {
    }

    @Override
    public WeatherInfo getWeatherInfo(Route route, LocalDateTime startDateTime) {
        var weatherForecast = weatherForecastDataProvider.getData(Map.of("latitude", route.centerLatitude(),
                "longitude", route.centerLongitude(), "date", getDate(startDateTime)));
        var hourlyRainData = new HourlyRainData(weatherForecast.rainHourly());

        var rainInfo = rainInfoProvider.getRainStartEnd(hourlyRainData)
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
        optimizeRoute(route, rainInfo, startDateTime);
        return WeatherInfo.builder()
                .generalData(generalData)
                .rainData(rainInfo)
                .windData(windData)
                .build();
    }

    @Override
    public void optimizeRoute(Route route, List<RainData> rainData, LocalDateTime startDateTime) {
        if (rainData.isEmpty()) {
            return;
        }

        log.debug("Optimizing route {} with weather data", route.name());
        var startHour = startDateTime.getHour();
        var optimizedPlaces = new ArrayList<>(List.of(route.places()));

        rainData.forEach(data -> processRainData(data, startHour, route, optimizedPlaces));
        route.updatePlaces(optimizedPlaces.toArray(new Place[0]));
    }

    private void processRainData(RainData rainData, int startHour, Route route, List<Place> optimizedPlaces) {
        var additionalPlaces = new ArrayList<>(List.of(route.additionalPlaces()));

        for (int i = startHour; i < startHour + optimizedPlaces.size(); i++) {
            var placeIndex = i - startHour;

            if (shouldLookForClosestPlace(rainData, i, optimizedPlaces.get(placeIndex).placeType())) {
                var place = optimizedPlaces.get(placeIndex);
                var xys = mapTo2DArray(additionalPlaces);
                var closestPlaceIndex = findClosestPlace(place, xys[0], xys[1]);

                if (closestPlaceIndex == -1) continue;
                var closestPlace = additionalPlaces.get(closestPlaceIndex);

                log.debug("Found place {} to replace with {}", place.name(), closestPlace.name());

                optimizedPlaces.add(placeIndex, closestPlace);
                additionalPlaces.remove(closestPlace);
                break;
            }
        }
    }

    private boolean shouldLookForClosestPlace(RainData data, int i, PlaceType placeType) {
        return isOverlappingWithRain(data, i) && !coveredPlaceTypes.contains(placeType);
    }

    private boolean isOverlappingWithRain(RainData data, int hour) {
        return hour >= data.getStartHour() && hour <= data.getEndHour();
    }
}
