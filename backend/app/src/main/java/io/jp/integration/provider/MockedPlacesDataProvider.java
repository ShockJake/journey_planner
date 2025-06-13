package io.jp.integration.provider;

import io.jp.integration.response.PlacesResponse;
import io.jp.mapper.PlacesResponseMapper;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MockedPlacesDataProvider implements DataProvider<PlacesResponse> {
    private final PlacesResponseMapper mapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public PlacesResponse getData(Map<String, Object> _ignored) {
        String data = fileUtils.readFile("/data/mocked/PlacesAPI_response.json");
        return mapper.map(data);
    }
}
