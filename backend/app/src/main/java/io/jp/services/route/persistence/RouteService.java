package io.jp.services.route.persistence;

import io.jp.cache.CachedMunicipalitiesProvider;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.route.RoutePlace;
import io.jp.database.repositories.place.PlaceRepository;
import io.jp.database.repositories.route.RouteRepository;
import io.jp.database.repositories.user.UserRouteRepository;
import io.jp.mapper.place.BoxedPlaceJpaMapper;
import io.jp.mapper.place.PlaceJpaMapper;
import io.jp.mapper.route.BoxedRouteJpaMapper;
import io.jp.mapper.route.RouteJpaMapper;
import io.jp.mapper.route.RoutePlaceMapper;
import io.jp.mapper.route.UserRouteMapper;
import io.jp.services.user.persistence.UserService;
import io.jp.services.user.persistence.UserService.UserUnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.jp.database.entities.route.RouteType.PREDEFINED;
import static java.util.Comparator.comparing;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final UserRouteRepository userRouteRepository;
    private final CachedMunicipalitiesProvider cachedMunicipalitiesProvider;
    private final BoxedRouteJpaMapper boxedRouteJpaMapper;
    private final RouteJpaMapper routeJpaMapper;
    private final BoxedPlaceJpaMapper boxedPlaceJpaMapper;
    private final PlaceJpaMapper placeJpaMapper;
    private final RoutePlaceMapper routePlaceMapper;
    private final UserRouteMapper userRouteMapper;
    private final UserService userService;

    public List<RouteBoxed> getPredefinedRoutes() {
        var jpaRoutes = routeRepository.findAllByRouteType(PREDEFINED);
        log.info("Found {} predefined routes", jpaRoutes.size());
        if (jpaRoutes.isEmpty()) {
            return List.of();
        }

        Map<Long, List<PlaceJpa>> placesByRouteId = jpaRoutes.stream()
                .collect(Collectors.toMap(RouteJpa::getRouteId, this::getJpaPlaces));

        return jpaRoutes.stream()
                .map(jpaRoute -> boxedRouteJpaMapper.mapFromJpa(jpaRoute, placesByRouteId.get(jpaRoute.getRouteId())))
                .toList();
    }

    @Transactional
    public void saveRouteBoxedToAccount(RouteBoxed route, Authentication authentication) {
        if (authentication == null) {
            log.error("Cannot save generated boxed route to nonexistent account");
            throw new UserUnauthorizedException();
        }
        var user = userService.findUserByUsername(authentication.getName());
        var savedPlaces = placeRepository.saveAll(route.places()
                .stream()
                .map(place -> boxedPlaceJpaMapper.mapToJpa(place, false))
                .toList());
        var savedAdditionalPlaces = placeRepository.saveAll(route.additionalPlaces()
                .stream()
                .map(place -> boxedPlaceJpaMapper.mapToJpa(place, true))
                .toList());
        var allSavedPlaces = concat(savedPlaces.stream(), savedAdditionalPlaces.stream()).toList();

        var municipality = cachedMunicipalitiesProvider.getCachedMunicipality(route.municipality());

        var jpaRoute = boxedRouteJpaMapper.mapToJpa(route, municipality);
        var routePlaces = range(0, allSavedPlaces.size()).mapToObj(index ->
                        routePlaceMapper.mapToRoutePlace(jpaRoute, allSavedPlaces.get(index), index))
                .toList();
        jpaRoute.setPlaces(routePlaces);
        var savedRoute = routeRepository.save(jpaRoute);
        userRouteRepository.save(userRouteMapper.mapToUserRoute(user, savedRoute));
    }

    public void saveRouteToAccount(Route route, Authentication authentication) {
        if (authentication == null) {
            log.error("Cannot save generated route to nonexistent account");
            throw new UserUnauthorizedException();
        }
        var user = userService.findUserByUsername(authentication.getName());
        var savedPlaces = placeRepository.saveAll(Stream.of(route.places())
                .map(placeJpaMapper::mapToJpa)
                .toList());

        var municipality = cachedMunicipalitiesProvider.getCachedMunicipality(route.municipality());

        var jpaRoute = routeJpaMapper.mapToJpa(route, municipality);
        var routePlaces = range(0, savedPlaces.size()).mapToObj(index ->
                        routePlaceMapper.mapToRoutePlace(jpaRoute, savedPlaces.get(index), index))
                .toList();
        jpaRoute.setPlaces(routePlaces);
        var savedRoute = routeRepository.save(jpaRoute);
        userRouteRepository.save(userRouteMapper.mapToUserRoute(user, savedRoute));
    }

    public RouteBoxed getBoxedRouteByName(String routeName) {
        var jpaRoute = getJpaRouteByName(routeName);
        var places = getJpaPlaces(jpaRoute);
        return boxedRouteJpaMapper.mapFromJpa(jpaRoute, places);
    }

    public Route getRouteByName(String routeName) {
        var jpaRoute = getJpaRouteByName(routeName);
        var places = getJpaPlaces(jpaRoute);
        return routeJpaMapper.mapFromJpa(jpaRoute, places);
    }

    public RouteJpa getJpaRouteByName(String routeName) {
        return routeRepository.findRouteJpaByName(routeName)
                .orElseThrow(() -> new RuntimeException("Route not found"));
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
