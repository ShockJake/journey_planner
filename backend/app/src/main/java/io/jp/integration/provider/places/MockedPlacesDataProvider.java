package io.jp.integration.provider.places;

import io.jp.mapper.PlacesResponseMapper;
import io.jp.rest.provider.DataProvider;
import io.jp.rest.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;


import static java.nio.charset.Charset.defaultCharset;

@Component
@RequiredArgsConstructor
public class MockedPlacesDataProvider implements DataProvider<PlacesResponse> {
    private final PlacesResponseMapper mapper;

    @Override
    public PlacesResponse getData(Map<String, Object> _ignored) {
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
