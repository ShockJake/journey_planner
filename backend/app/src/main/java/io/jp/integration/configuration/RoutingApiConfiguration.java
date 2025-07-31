package io.jp.integration.configuration;

import io.jp.core.domain.Path;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.routing.MockedRoutingDataProvider;
import io.jp.integration.provider.routing.RoutingDataProvider;
import io.jp.mapper.other.PathMapper;
import io.jp.utils.PropertiesProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RoutingApiConfiguration {

    @Bean
    @ConditionalOnProperty(name="integrations.api.routing.enabled", havingValue = "false")
    public DataProvider<Path> mockedRoutingDataProvider(PathMapper pathMapper) {
        log.info("Using mocked implementation of Routing Data Provider");
        return new MockedRoutingDataProvider(pathMapper);
    }

    @Bean
    @ConditionalOnProperty(name="integrations.api.routing.enabled", havingValue = "true")
    public DataProvider<Path> routingDataProvider(PathMapper pathMapper, PropertiesProvider propertiesProvider) {
        log.info("Using real implementation of Routing Data Provider");
        return new RoutingDataProvider(propertiesProvider, pathMapper);
    }
}
