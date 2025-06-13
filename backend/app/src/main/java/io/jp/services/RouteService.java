package io.jp.services;

import io.jp.core.domain.Route;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.route.RoutePlace;
import io.jp.database.repositories.MunicipalityRepository;
import io.jp.database.repositories.PlaceRepository;
import io.jp.database.repositories.RouteRepository;
import io.jp.database.repositories.UserRouteRepository;
import io.jp.mapper.PlaceJpaMapper;
import io.jp.mapper.RouteJpaMapper;
import io.jp.mapper.RoutePlaceMapper;
import io.jp.mapper.UserRouteMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jp.cache.CachedMunicipalitiesProvider.getCachedMunicipality;
import static io.jp.cache.CachedMunicipalitiesProvider.hasNoCachedMunicipalities;
import static io.jp.cache.CachedMunicipalitiesProvider.putCachedMunicipalities;
import static io.jp.database.entities.route.RouteType.PREDEFINED;
import static java.util.Comparator.comparing;
import static java.util.stream.IntStream.range;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final MunicipalityRepository municipalityRepository;
    private final UserRouteRepository userRouteRepository;
    private final RouteJpaMapper routeJpaMapper;
    private final PlaceJpaMapper placeJpaMapper;
    private final RoutePlaceMapper routePlaceMapper;
    private final UserRouteMapper userRouteMapper;
    private final UserService userService;

    public List<Route> getPredefinedRoutes() {
        var jpaRoutes = routeRepository.findAllByRouteType(PREDEFINED);
        if (jpaRoutes.isEmpty()) {
            return List.of();
        }

        Map<Long, List<PlaceJpa>> placesByRouteId = jpaRoutes.stream()
                .collect(Collectors.toMap(RouteJpa::getRouteId, this::getJpaPlaces));
        log.info("Found {} predefined routes", placesByRouteId.size());

        return jpaRoutes.stream()
                .map(jpaRoute -> routeJpaMapper.mapFromJpa(jpaRoute, placesByRouteId.get(jpaRoute.getRouteId())))
                .toList();
    }

    @Transactional
    public void saveRouteToAccount(Route route, String userName) {
        var user = userService.findUserByUsername(userName);
        var savedPlaces = placeRepository.saveAll(route.places()
                .stream()
                .map(placeJpaMapper::mapToJpa)
                .toList());

        if (hasNoCachedMunicipalities()) {
            putCachedMunicipalities(municipalityRepository.findAll());
        }
        var municipality = getCachedMunicipality(route.municipality());

        var jpaRoute = routeJpaMapper.mapToJpa(route, municipality);
        var routePlaces = range(0, savedPlaces.size()).mapToObj(index ->
                        routePlaceMapper.mapToRoutePlace(jpaRoute, savedPlaces.get(index), index))
                .toList();
        jpaRoute.setPlaces(routePlaces);
        var savedRoute = routeRepository.save(jpaRoute);
        userRouteRepository.save(userRouteMapper.mapToUserRoute(user, savedRoute));
    }

    public Route getJpaRouteByName(String routeName) {
        var jpaRoute = routeRepository.findRouteJpaByName(routeName)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        var places = getJpaPlaces(jpaRoute);
        return routeJpaMapper.mapFromJpa(jpaRoute, places);
    }

    private List<PlaceJpa> getJpaPlaces(RouteJpa routeJpa) {
        var routePlaces = routeJpa.getPlaces();
        if (routePlaces.isEmpty()) {
            return List.of();
        }

        return routePlaces.stream()
                .sorted(comparing(RoutePlace::getPlaceIndex))
                .map(RoutePlace::getPlace)
                .toList();
    }
}
