package io.jp.integration.configuration;

import io.jp.integration.provider.DataProvider;
import io.jp.integration.provider.places.MockedPlacesDataProvider;
import io.jp.integration.provider.places.PlacesDataProvider;
import io.jp.integration.response.PlacesResponse;
import io.jp.mapper.place.PlacesResponseMapper;
import io.jp.utils.PropertiesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlacesApiConfiguration {

    @ConditionalOnProperty(name = "integrations.api.places.enabled", havingValue = "false")
    public DataProvider<PlacesResponse> mockedPlacesDataProvider(PlacesResponseMapper placesResponseMapper) {
        return new MockedPlacesDataProvider(placesResponseMapper);
    }

    @ConditionalOnProperty(name = "integrations.api.places.enabled", havingValue = "true")
    public DataProvider<PlacesResponse> placesDataProvider(PlacesResponseMapper placesResponseMapper, PropertiesProvider propertiesProvider) {
        return new PlacesDataProvider(propertiesProvider, placesResponseMapper);
    }
}
