package io.jp.cache;

import io.jp.core.domain.OptimizedRoute;

import java.util.HashMap;
import java.util.Map;

public class CachedOptimizedRouteProvider {
    private static final Map<String, OptimizedRoute> cachedOptimizedRoutes = new HashMap<>();
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
}
