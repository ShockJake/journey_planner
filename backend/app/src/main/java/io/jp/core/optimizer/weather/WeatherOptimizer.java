package io.jp.core.optimizer.weather;

import io.jp.core.domain.Place;
import io.jp.core.domain.Route;
import io.jp.core.domain.weather.GeneralData;
import io.jp.core.domain.weather.RainData;
import io.jp.core.domain.weather.WeatherForecast;
import io.jp.core.domain.weather.WeatherInfo;
import io.jp.database.entities.route.PlaceType;
import io.jp.integration.provider.DataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.awt.geom.Point2D.Double;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static io.jp.database.entities.route.PlaceType.BUILDING;
import static io.jp.database.entities.route.PlaceType.CHURCH;
import static io.jp.database.entities.route.PlaceType.COFFEE;
import static io.jp.database.entities.route.PlaceType.MUSEUM;
import static io.jp.database.entities.route.PlaceType.STORE;
import static io.jp.utils.DateTimeUtils.getDate;
import static java.lang.Double.MAX_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherOptimizer {
    private final RainInfoProvider rainInfoProvider;
    private final WindInfoRetriever windInfoRetriever;
    private final DataProvider<WeatherForecast> weatherForecastDataProvider;
    private final List<PlaceType> coveredPlaceTypes = List.of(CHURCH, BUILDING, MUSEUM, STORE, COFFEE);

    public WeatherInfo getWeatherInfo(Route route, LocalDateTime startDateTime) {
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
        optimizeRoute(route, startDateTime, rainInfo);
        return WeatherInfo.builder()
                .generalData(generalData)
                .rainData(rainInfo)
                .windData(windData)
                .build();
    }

    private void optimizeRoute(Route route, LocalDateTime startDateTime, List<RainData> rainData) {
        if (rainData.isEmpty()) {
            return;
        }
        var startHour = startDateTime.getHour();

        var optimizedPlaces = new ArrayList<>(route.places());
        var additionalPlaces = new ArrayList<>(route.additionalPlaces());

        rainData.forEach(data -> {
            for (int i = startHour; i < startHour + optimizedPlaces.size(); i++) {
                var placeIndex = i - startHour;
                if (isOverlappingWithRain(data, i) && !coveredPlaceTypes.contains(optimizedPlaces.get(placeIndex).placeType())) {
                    var place = optimizedPlaces.get(placeIndex);
                    var closestPlace = findClosestAdditionalPlace(place, additionalPlaces);
                    optimizedPlaces.add(placeIndex, closestPlace);
                    additionalPlaces.remove(closestPlace);
                    break;
                }
            }
        });
        route.updatePlaces(optimizedPlaces);
    }

    private Place findClosestAdditionalPlace(Place start, List<Place> places) {
        var startPoint = new Double(start.position().lat(), start.position().lng());
        Place closest = null;
        double minDistance = MAX_VALUE;
        for (var place : places) {
            var point = place.position();
            double distance = startPoint.distance(point.lat(), point.lng());
            if (distance < minDistance) {
                minDistance = distance;
                closest = place;
            }
        }
        return closest;
    }

    private boolean isOverlappingWithRain(RainData data, int hour) {
        return hour >= data.getStartHour() && hour <= data.getEndHour();
    }
}
