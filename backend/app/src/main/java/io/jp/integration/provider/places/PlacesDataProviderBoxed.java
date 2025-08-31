package io.jp.integration.provider.places;

import io.jp.core.domain.point.Point;
import io.jp.core.domain.point.PointBoxed;
import io.jp.core.domain.route.RouteLongevity;
import io.jp.database.entities.route.PlaceType;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponseBoxed;
import io.jp.mapper.place.PlacesResponseMapperBoxed;
import io.jp.utils.PropertiesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jp.integration.common.RestClientProvider.getRestClient;
import static io.jp.utils.PropertiesProvider.ROUTING_API_KEY_PROPERTY_NAME;

@Slf4j
@RequiredArgsConstructor
public class PlacesDataProviderBoxed implements DataProvider<PlacesResponseBoxed> {
    private static final String BASE_URL = "https://api.geoapify.com/v2/places";
    private static final String RADIUS_METERS = "1500";
    private final PropertiesProvider propertiesProvider;
    private final PlacesResponseMapperBoxed mapper;

    private final RestClient restClient = getRestClient();

    @Override
    public PlacesResponseBoxed getData(Map<String, Object> input) {
        var finalUrl = BASE_URL + resolveParams(input);
        log.info("Getting places data for: {}", input.get("placeTypes"));

        var response = restClient.get()
                .uri(finalUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        var responseCode = response.getStatusCode();

        if (responseCode != HttpStatus.OK) {
            log.error("Routing response is {}", responseCode);
            throw new HttpClientErrorException(responseCode);
        }

        return mapper.map(response.getBody());
    }

    @SuppressWarnings("unchecked")
    private String resolveParams(Map<String, Object> input) {
        var placeTypes = (List<PlaceType>) input.get("placeTypes");
        var location = mapLocation(input.get("location"));
        var longevity = (RouteLongevity) input.get("longevity");

        var filter = "filter=circle:%s,%s,%s".formatted(location.lng(), location.lat(), RADIUS_METERS);
        var limit = "limit=" + (longevity.getNumberOfPlaces() + 4);
        var categories = "categories=%s".formatted(mapCategories(placeTypes));
        var key = "apiKey=" + propertiesProvider.getProperty(ROUTING_API_KEY_PROPERTY_NAME);

        return "?%s&%s&%s&%s".formatted(categories, filter, limit, key);
    }

    private String mapCategories(List<PlaceType> placeTypes) {
        return placeTypes.stream()
                .map(PlaceType::getCategory)
                .collect(Collectors.joining(","));
    }

    private Point mapLocation(Object point) {
       if (point instanceof PointBoxed(Double lat, Double lng)) {
           return Point.of(lat, lng);
       }
       if (point instanceof Point) {
           return (Point) point;
       }
       throw new IllegalArgumentException("Invalid point type");
    }
}
