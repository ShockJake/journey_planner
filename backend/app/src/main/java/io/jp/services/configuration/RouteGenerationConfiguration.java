package io.jp.services.configuration;

import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.core.organizer.PlaceOrganizer;
import io.jp.core.organizer.PlaceOrganizerBoxed;
import io.jp.database.repositories.place.MunicipalityRepository;
import io.jp.integration.provider.DataProvider;
import io.jp.integration.response.PlacesResponse;
import io.jp.integration.response.PlacesResponseBoxed;
import io.jp.services.route.generation.RouteGenerationService;
import io.jp.services.route.generation.RouteGenerationServiceNotOpt;
import io.jp.services.route.generation.RouteGenerationServiceOpt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RouteGenerationConfiguration {
    @Bean
    @ConditionalOnProperty(name = "service.implementation.route.generation", havingValue = "opt")
    public RouteGenerationService<Route> routeGenerationServiceOpt(MunicipalityRepository municipalityRepository,
                                                                   DataProvider<PlacesResponse> placesDataProvider,
                                                                   PlaceOrganizer placeOrganizer) {
        return new RouteGenerationServiceOpt(municipalityRepository, placesDataProvider, placeOrganizer);
    }

    @Bean
    @ConditionalOnProperty(name = "service.implementation.route.generation", havingValue = "no-opt")
    public RouteGenerationService<RouteBoxed> routeGenerationServiceNoOpt(MunicipalityRepository municipalityRepository,
                                                                          DataProvider<PlacesResponseBoxed> placesDataProvider,
                                                                          PlaceOrganizerBoxed placeOrganizer) {
        return new RouteGenerationServiceNotOpt(municipalityRepository, placesDataProvider, placeOrganizer);
    }
}
