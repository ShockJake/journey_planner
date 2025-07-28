package io.jp.integration.configuration;

import io.jp.core.domain.Path;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.routing.MockedRoutingDataProvider;
import io.jp.integration.provider.routing.RoutingDataProvider;
import io.jp.mapper.other.PathMapper;
import io.jp.utils.PropertiesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingApiConfiguration {
    @ConditionalOnProperty(name="integrations.api.routing.enabled", havingValue = "false")
    public DataProvider<Path> mockedRoutingDataProvider(PathMapper pathMapper) {
        return new MockedRoutingDataProvider(pathMapper);
    }

    @ConditionalOnProperty(name="integrations.api.routing.enabled", havingValue = "true")
    public DataProvider<Path> routingDataProvider(PathMapper pathMapper, PropertiesProvider propertiesProvider) {
        return new RoutingDataProvider(propertiesProvider, pathMapper);
    }
}
