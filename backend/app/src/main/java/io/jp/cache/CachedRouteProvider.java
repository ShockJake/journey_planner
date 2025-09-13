package io.jp.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteBoxed;
import io.jp.services.route.persistence.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CachedRouteProvider {
    private final RouteService routeService;
    private final Cache<String, Route> routeCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .build();

    private static final Map<String, Route> CACHED_ROUTES = new HashMap<>();
    private static final Map<String, RouteBoxed> CACHED_BOXED_ROUTES = new HashMap<>();
    private static final Object LOCK = new Object();

    public Route getCachedRoute(String routeName) {
        return routeCache.get(routeName, routeService::getRouteByName);
    }

    public void putCachedRoute(String routeName, Route route) {
        routeCache.put(routeName, route);
    }

    public static RouteBoxed getCachedRouteBoxed(String routeName) {
        synchronized (LOCK) {
            log.info("Getting cached boxed route: {}", routeName);
            return CACHED_BOXED_ROUTES.get(routeName).copy();
        }
    }

    public static void putCachedRoute(String routeName, RouteBoxed route) {
        synchronized (LOCK) {
            log.info("Setting cached boxed route: {}", routeName);
            CACHED_BOXED_ROUTES.put(routeName, route);
        }
    }

    public static boolean isRouteBoxedCached(String routeName) {
        synchronized (LOCK) {
            return CACHED_BOXED_ROUTES.containsKey(routeName);
        }
    }
}
