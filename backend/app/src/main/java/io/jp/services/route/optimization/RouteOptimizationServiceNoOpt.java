package io.jp.services.route.optimization;

import io.jp.api.dto.RouteOptimizationRequest;
import io.jp.cache.CachedRouteProvider;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.core.optimization.route.RouteOptimizer;
import io.jp.services.route.persistence.RouteService;
import lombok.extern.slf4j.Slf4j;

import static io.jp.cache.CachedRouteProvider.getCachedRouteBoxed;
import static io.jp.cache.CachedRouteProvider.isRouteBoxedCached;

@Slf4j
public record RouteOptimizationServiceNoOpt(RouteService routeService,
                                            RouteOptimizer<RouteBoxed, OptimizedRouteBoxed> routeOptimizer)
        implements RouteOptimizationService<OptimizedRouteBoxed> {

    @Override
    public OptimizedRouteBoxed optimizeRoute(RouteOptimizationRequest request) {
        log.debug("Optimizing route (non-optimized component): {}", request.routeName());
        RouteBoxed route;
        if (isRouteBoxedCached(request.routeName())) {
            route = getCachedRouteBoxed(request.routeName());
        } else {
            route = routeService.getBoxedRouteByName(request.routeName());
        }
        return routeOptimizer.optimizeRoute(route.copy(), request.startDateTime());
    }
}
