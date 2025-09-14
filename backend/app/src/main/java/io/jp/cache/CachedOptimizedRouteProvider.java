package io.jp.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        log.info("Caching optimized route {}", optimizedRoute.optimizationId());
        CACHE.put(optimizedRoute.optimizationId(), optimizedRoute);
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
