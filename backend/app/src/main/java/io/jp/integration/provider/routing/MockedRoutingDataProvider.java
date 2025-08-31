package io.jp.integration.provider.routing;

import io.jp.core.domain.path.Path;
import io.jp.integration.provider.DataProvider;
import io.jp.mapper.path.PathMapper;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;

import java.util.Map;

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
