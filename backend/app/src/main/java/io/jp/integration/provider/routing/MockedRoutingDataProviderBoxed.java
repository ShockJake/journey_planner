package io.jp.integration.provider.routing;

import io.jp.core.domain.path.PathBoxed;
import io.jp.mapper.path.PathBoxedMapper;
import io.jp.integration.provider.DataProvider;
import io.jp.utils.FileUtils;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MockedRoutingDataProviderBoxed implements DataProvider<PathBoxed> {
    private final PathBoxedMapper pathMapper;
    private final FileUtils fileUtils = new FileUtils();

    @Override
    public PathBoxed getData(Map<String, Object> input) {
        var inputData = fileUtils.readFile("/data/mocked/routing_api_response.json");
        return pathMapper.map(inputData);
    }
}
