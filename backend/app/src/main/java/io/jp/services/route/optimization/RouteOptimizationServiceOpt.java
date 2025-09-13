package io.jp.services.route.optimization;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.cache.CachedRouteProvider;
import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.route.Route;
import io.jp.core.optimization.route.RouteOptimizer;
import io.jp.services.route.persistence.RouteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record RouteOptimizationServiceOpt(RouteOptimizer<Route, OptimizedRoute> routeOptimizer, RouteService routeService,
                                          CachedRouteProvider cachedRouteProvider)
        implements RouteOptimizationService<OptimizedRoute> {

    @Override
    public OptimizedRoute optimizeRoute(RouteOptimizationRequest request) {
        var routeName = request.routeName();
        log.debug("Optimizing route (optimized component): {}", routeName);
        var route = cachedRouteProvider.getCachedRoute(routeName);
        return routeOptimizer.optimizeRoute(route.copy(), request.startDateTime());
    }
}
