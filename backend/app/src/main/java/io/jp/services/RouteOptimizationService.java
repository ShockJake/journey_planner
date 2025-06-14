package io.jp.services;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.core.RouteOptimizer;
import io.jp.core.domain.OptimizedRoute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.jp.cache.CachedRouteProvider.getCachedRoute;
import static io.jp.cache.CachedRouteProvider.isRouteCached;
import static io.jp.cache.CachedRouteProvider.putCachedRoute;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteOptimizationService {
    private final RouteOptimizer routeOptimizer;
    private final RouteService routeService;

    public OptimizedRoute optimizeRoute(RouteOptimizationRequest request) {
        var routeName = request.routeName();
        if (isRouteCached(routeName)) {
            log.info("Getting route '{}' from cache", routeName);
            return routeOptimizer.optimizeRoute(getCachedRoute(routeName).copy(), request.startDateTime());
        }
        log.info("Getting route '{}' from database", routeName);
        var route = routeService.getJpaRouteByName(routeName);
        putCachedRoute(routeName, route);
        return routeOptimizer.optimizeRoute(route.copy(), request.startDateTime());
    }
}
