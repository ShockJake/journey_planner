package io.jp.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CachedOptimizedRouteProvider {
    private static final Map<String, OptimizedRouteBoxed> cachedOptimizedBoxedRoutes = new HashMap<>();
    private static final Object LOCK = new Object();
    private static final Cache<String, OptimizedRoute> CACHE = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofDays(1))
            .build();

    public static OptimizedRoute getOptimizedRouteFromCache(String routeId) {
        return CACHE.getIfPresent(routeId);
    }

    public static void putOptimizedRouteToCache(OptimizedRoute optimizedRoute) {
        CACHE.put(optimizedRoute.route().name(), optimizedRoute);
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
