package io.jp.integration.configuration;

import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.places.MockedPlacesDataProvider;
import io.jp.integration.provider.places.MockedPlacesDataProviderBoxed;
import io.jp.integration.provider.places.PlacesDataProvider;
import io.jp.integration.provider.places.PlacesDataProviderBoxed;
import io.jp.integration.response.PlacesResponse;
import io.jp.integration.response.PlacesResponseBoxed;
import io.jp.mapper.place.PlacesResponseMapper;
import io.jp.mapper.place.PlacesResponseMapperBoxed;
import io.jp.utils.PropertiesProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PlacesApiConfiguration {

    @Bean
    @ConditionalOnExpression(
            "'${integrations.api.places.enabled}'.equals('false') and '${service.implementation.route.generation}'.equals('no-opt')"
    )
    public DataProvider<PlacesResponseBoxed> mockedPlacesDataProviderBoxed(PlacesResponseMapperBoxed placesResponseMapper) {
        log.info("Using mocked implementation of boxed Routing Data Provider");
        return new MockedPlacesDataProviderBoxed(placesResponseMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.places.enabled} and '${service.implementation.route.generation}'.equals('no-opt')"
    )
    public DataProvider<PlacesResponse> mockedPlacesDataProvider(PlacesResponseMapper placesResponseMapper) {
        log.info("Using mocked implementation of Routing Data Provider");
        return new MockedPlacesDataProvider(placesResponseMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.places.enabled} and '${service.implementation.route.generation}'.equals('no-opt')"
    )
    public DataProvider<PlacesResponseBoxed> placesDataProviderBoxed(PlacesResponseMapperBoxed placesResponseMapper, PropertiesProvider propertiesProvider) {
        log.info("Using real implementation of Boxed Places Data Provider");
        return new PlacesDataProviderBoxed(propertiesProvider, placesResponseMapper);
    }

    @Bean
    @ConditionalOnExpression(
            "${integrations.api.places.enabled} and '${service.implementation.route.generation}'.equals('opt')"
    )
    public DataProvider<PlacesResponse> placesDataProvider(PlacesResponseMapper placesResponseMapper, PropertiesProvider propertiesProvider) {
        log.info("Using real implementation of Places Data Provider");
        return new PlacesDataProvider(propertiesProvider, placesResponseMapper);
    }
}
