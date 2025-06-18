package io.jp.services;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.core.RouteOptimizer;
import io.jp.core.domain.OptimizedRoute;
import io.jp.database.entities.route.OptimizedRouteJpa;
import io.jp.database.entities.user.User;
import io.jp.database.repositories.route.OptimizedRouteRepository;
import io.jp.mapper.OptimizedRouteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static io.jp.cache.CachedRouteProvider.getCachedRoute;
import static io.jp.cache.CachedRouteProvider.isRouteCached;
import static io.jp.cache.CachedRouteProvider.putCachedRoute;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteOptimizationService {
    private final RouteOptimizer routeOptimizer;
    private final RouteService routeService;
    private final OptimizedRouteMapper optimizedRouteMapper;
    private final OptimizedRouteRepository optimizedRouteRepository;

    public OptimizedRoute optimizeRoute(RouteOptimizationRequest request) {
        var routeName = request.routeName();
        log.debug("Optimizing route: {}", routeName);
        if (isRouteCached(routeName)) {
            return routeOptimizer.optimizeRoute(getCachedRoute(routeName).copy(), request.startDateTime());
        }
        var route = routeService.getRouteByName(routeName);
        putCachedRoute(routeName, route);
        return routeOptimizer.optimizeRoute(route.copy(), request.startDateTime());
    }

    public List<OptimizedRoute> getOptimizedRoutesByUser(User user) {
        log.debug("Getting optimized routes connected to user {}", user.getUsername());
        return optimizedRouteRepository.findAllByUser(user).stream()
                .map(optimizedRouteMapper::mapFromJpa)
                .collect(Collectors.toList());
    }

    public OptimizedRouteJpa saveOptimizedRoute(OptimizedRoute optimizedRoute, User user) {
        log.debug("Saving optimized route {} for user {}", optimizedRoute.route().name(), user.getUsername());
        var jpaRoute = routeService.getJpaRouteByName(optimizedRoute.route().name());
        var optimizedRouteJpa = optimizedRouteMapper.mapToJpa(optimizedRoute, jpaRoute, user);
        return optimizedRouteRepository.save(optimizedRouteJpa);
    }
}
