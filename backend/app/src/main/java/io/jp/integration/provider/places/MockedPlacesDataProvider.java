package io.jp.integration.provider.places;

import io.jp.mapper.place.PlacesResponseMapper;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;


import static java.nio.charset.Charset.defaultCharset;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockedPlacesDataProvider implements DataProvider<PlacesResponse> {
    private final PlacesResponseMapper mapper;

    @Override
    public PlacesResponse getData(Map<String, Object> input) {
        log.info("Getting PlacesAPI data for {}", input);

        String data = Optional.ofNullable(this.getClass().getResourceAsStream("/data/mocked/PlacesAPI_response.json"))
                .flatMap(this::readDataFromResource)
                .orElseThrow(() -> new RuntimeException("No response from Places API "));
        return mapper.map(data);
    }

    private Optional<String> readDataFromResource(InputStream inputStream) {
        try {
            return Optional.of(IOUtils.toString(inputStream, defaultCharset()));
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}
