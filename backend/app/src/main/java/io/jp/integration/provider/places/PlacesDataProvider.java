package io.jp.integration.provider.places;

import io.jp.core.domain.Point;
import io.jp.core.domain.RouteLongevity;
import io.jp.database.entities.route.PlaceType;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponse;
import io.jp.mapper.place.PlacesResponseMapper;
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
public class PlacesDataProvider implements DataProvider<PlacesResponse> {
    private static final String BASE_URL = "https://api.geoapify.com/v2/places";
    private final PropertiesProvider propertiesProvider;
    private final PlacesResponseMapper mapper;

    private final RestClient restClient = getRestClient();

    @Override
    public PlacesResponse getData(Map<String, Object> input) {
        var finalUrl = BASE_URL + resolveParams(input);
        log.info("Getting places data for: {}", finalUrl);

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
        var location = (Point) input.get("location");
        var longevity = (RouteLongevity) input.get("longevity");

        var filter = "filter=circle:%s,%s".formatted(location.lng(), location.lat());
        var limit = "limit=" + longevity.getNumberOfPlaces() + 4;
        var categories = "categories=%s".formatted(mapCategories(placeTypes));
        var key = propertiesProvider.getProperty(ROUTING_API_KEY_PROPERTY_NAME);

        return "?%s&%s&%s&%s".formatted(categories, filter, limit, key);

    }

    private String mapCategories(List<PlaceType> placeTypes) {
        return placeTypes.stream()
                .map(PlaceType::getCategory)
                .collect(Collectors.joining(","));
    }
}
