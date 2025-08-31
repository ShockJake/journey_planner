package io.jp.integration.provider.routing;

import io.jp.core.domain.path.Path;
import io.jp.core.domain.place.Place;
import io.jp.core.domain.place.PlaceBoxed;
import io.jp.integration.provider.DataProvider;
import io.jp.mapper.path.PathMapper;
import io.jp.utils.PropertiesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jp.integration.common.RestClientProvider.getRestClient;
import static io.jp.utils.PropertiesProvider.ROUTING_API_KEY_PROPERTY_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RequiredArgsConstructor
public class RoutingDataProvider implements DataProvider<Path> {
    private static final String BASE_URL = "https://api.geoapify.com/v1/routing";

    private final PropertiesProvider propertiesProvider;
    private final PathMapper pathMapper;

    private final RestClient restClient = getRestClient();

    @Override
    public Path getData(Map<String, Object> input) {
        var params = resolveParameters((Place[]) input.get("places"));
        var finalUrl = BASE_URL + params;
        log.info("Getting routing");
        var response = restClient.get()
                .uri(finalUrl)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        var responseCode = response.getStatusCode();

        if (responseCode != HttpStatus.OK) {
            log.error("Routing response is {}", responseCode);
            throw new HttpClientErrorException(responseCode);
        }

        return pathMapper.map(response.getBody());
    }

    private String resolveParameters(Place[] places) {
        var key = propertiesProvider.getProperty(ROUTING_API_KEY_PROPERTY_NAME);
        var waypoints = Stream.of(places)
                .map(place -> "%s,%s".formatted(place.latitude(), place.longitude()))
                .collect(Collectors.joining("|"));
        log.info(waypoints);
        return "?waypoints=%s&mode=walk&apiKey=%s".formatted(waypoints, key);
    }
}
