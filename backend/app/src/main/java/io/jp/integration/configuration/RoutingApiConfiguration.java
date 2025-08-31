package io.jp.integration.configuration;

import io.jp.core.domain.path.Path;
import io.jp.core.domain.path.PathBoxed;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.routing.MockedRoutingDataProvider;
import io.jp.integration.provider.routing.MockedRoutingDataProviderBoxed;
import io.jp.integration.provider.routing.BoxedRoutingDataProvider;
import io.jp.integration.provider.routing.RoutingDataProvider;
import io.jp.mapper.path.PathBoxedMapper;
import io.jp.mapper.path.PathMapper;
import io.jp.utils.PropertiesProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RoutingApiConfiguration {

    @Bean
    @ConditionalOnExpression(
            "'${integrations.api.routing.enabled}'.equals('false') and '${service.implementation.route.optimization}'.equals('no-opt')"
    )
    public DataProvider<PathBoxed> mockedRoutingDataProviderBoxed(PathBoxedMapper pathMapper) {
        log.info("Using mocked implementation of boxed Routing Data Provider");
        return new MockedRoutingDataProviderBoxed(pathMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "'${integrations.api.routing.enabled}'.equals('false') and '${service.implementation.route.optimization}'.equals('opt')"
    )
    public DataProvider<Path> mockedRoutingDataProvider(PathMapper pathMapper) {
        log.info("Using mocked implementation of Routing Data Provider");
        return new MockedRoutingDataProvider(pathMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.routing.enabled} and '${service.implementation.route.optimization}'.equals('no-opt')"
    )
    public DataProvider<PathBoxed> routingDataProviderBoxed(PathBoxedMapper pathMapper, PropertiesProvider propertiesProvider) {
        log.info("Using real implementation of boxed Routing Data Provider");
        return new BoxedRoutingDataProvider(propertiesProvider, pathMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.routing.enabled} and '${service.implementation.route.optimization}'.equals('opt')"
    )
    public DataProvider<Path> routingDataProvider(PathMapper pathMapper, PropertiesProvider propertiesProvider) {
        log.info("Using real implementation of Routing Data Provider");
        return new RoutingDataProvider(propertiesProvider, pathMapper);
    }
}
