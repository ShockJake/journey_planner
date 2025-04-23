package io.jp.services;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.core.RouteOptimizer;
import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Route;
import io.jp.database.entities.route.PlaceJpa;
import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.route.RoutePlace;
import io.jp.database.repositories.RouteRepository;
import io.jp.mapper.RouteJpaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jp.database.entities.route.RouteType.PREDEFINED;
import static io.jp.services.CachedRouteProvider.getCachedRoute;
import static io.jp.services.CachedRouteProvider.isRouteCached;
import static io.jp.services.CachedRouteProvider.putCachedRoute;
import static java.util.Comparator.comparing;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteJpaMapper routeJpaMapper;
    private final RouteOptimizer routeOptimizer;

    public List<Route> getPredefinedRoutes() {
        var jpaRoutes = routeRepository.findAllByRouteType(PREDEFINED);
        if (jpaRoutes.isEmpty()) {
            return List.of();
        }

        Map<Long, List<PlaceJpa>> placesByRouteId = jpaRoutes.stream()
                .collect(Collectors.toMap(RouteJpa::getRouteId, this::getJpaPlaces));
        log.info("Found {} routes", placesByRouteId.size());

        return jpaRoutes.stream()
                .map(jpaRoute -> routeJpaMapper.mapFromJpa(jpaRoute, placesByRouteId.get(jpaRoute.getRouteId())))
                .toList();
    }

    public OptimizedRoute optimizeRoute(RouteOptimizationRequest request) {
        var routeName = request.routeName();
        if (isRouteCached(routeName)) {
            return routeOptimizer.optimizeRoute(getCachedRoute(routeName), request.startDateTime());
        }
        var route = getJpaRouteByName(routeName);
        putCachedRoute(routeName, route);
        return routeOptimizer.optimizeRoute(route, request.startDateTime());
    }

    private Route getJpaRouteByName(String routeName) {
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
