package io.jp.integration.configuration;

import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.places.MockedPlacesDataProvider;
import io.jp.integration.provider.places.PlacesDataProvider;
import io.jp.integration.response.PlacesResponse;
import io.jp.mapper.place.PlacesResponseMapper;
import io.jp.utils.PropertiesProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PlacesApiConfiguration {

    @Bean
    @ConditionalOnProperty(name = "integrations.api.places.enabled", havingValue = "false")
    public DataProvider<PlacesResponse> mockedPlacesDataProvider(PlacesResponseMapper placesResponseMapper) {
        log.info("Using mocked implementation of Routing Data Provider");
        return new MockedPlacesDataProvider(placesResponseMapper);
    }

    @Bean
    @ConditionalOnProperty(name = "integrations.api.places.enabled", havingValue = "true")
    public DataProvider<PlacesResponse> placesDataProvider(PlacesResponseMapper placesResponseMapper, PropertiesProvider propertiesProvider) {
        log.info("Using real implementation of Places Data Provider");
        return new PlacesDataProvider(propertiesProvider, placesResponseMapper);
    }
}
