package io.jp.rest.provider.routing;

import io.jp.core.domain.Path;
import io.jp.mapper.PathMapper;
import io.jp.rest.provider.DataProvider;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MockedRoutingDataProvider implements DataProvider<Path> {
    private final PathMapper pathMapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public Path getData(Map<String, Object> input) {
        var inputData = fileUtils.readFile("/data/mocked/routing_api_response.json");
        return pathMapper.map(inputData);
    }
}
