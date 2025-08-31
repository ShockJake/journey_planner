package io.jp.cache;

import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;

import java.util.HashMap;
import java.util.Map;

public class CachedOptimizedRouteProvider {
    private static final Map<String, OptimizedRoute> cachedOptimizedRoutes = new HashMap<>();
    private static final Map<String, OptimizedRouteBoxed> cachedOptimizedBoxedRoutes = new HashMap<>();
    private static final Object LOCK = new Object();

    public static OptimizedRoute getOptimizedRouteFromCache(String routeId) {
        synchronized (LOCK) {
            return cachedOptimizedRoutes.get(routeId);
        }
    }

    public static void putOptimizedRouteToCache(OptimizedRoute optimizedRoute) {
        synchronized (LOCK) {
            cachedOptimizedRoutes.put(optimizedRoute.optimizationId(), optimizedRoute);
        }
    }

    public static OptimizedRouteBoxed getOptimizedRouteBoxedFromCache(String routeId) {
        synchronized (LOCK) {
            return cachedOptimizedBoxedRoutes.get(routeId);
        }
    }

    public static void putOptimizedRouteToCache(OptimizedRouteBoxed optimizedRoute) {
        synchronized (LOCK) {
            cachedOptimizedBoxedRoutes.put(optimizedRoute.optimizationId(), optimizedRoute);
        }
    }
}
