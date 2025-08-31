package io.jp.services.route.optimization;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.route.Route;
import io.jp.core.optimization.route.RouteOptimizer;
import io.jp.services.route.persistence.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.jp.cache.CachedRouteProvider.getCachedRoute;
import static io.jp.cache.CachedRouteProvider.isRouteCached;
import static io.jp.cache.CachedRouteProvider.putCachedRoute;

@Slf4j
public record RouteOptimizationServiceOpt(RouteOptimizer<Route, OptimizedRoute> routeOptimizer, RouteService routeService)
        implements RouteOptimizationService<OptimizedRoute> {

    @Override
    public OptimizedRoute optimizeRoute(RouteOptimizationRequest request) {
        var routeName = request.routeName();
        log.debug("Optimizing route (optimized component): {}", routeName);
        if (isRouteCached(routeName)) {
            return routeOptimizer.optimizeRoute(getCachedRoute(routeName), request.startDateTime());
        }
        var route = routeService.getRouteByName(routeName);
        putCachedRoute(routeName, route);
        return routeOptimizer.optimizeRoute(route.copy(), request.startDateTime());
    }
}
