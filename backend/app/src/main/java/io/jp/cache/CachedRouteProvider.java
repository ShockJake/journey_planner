package io.jp.cache;

import io.jp.core.domain.route.Route;
import io.jp.core.domain.route.RouteBoxed;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CachedRouteProvider {
    private static final Map<String, Route> CACHED_ROUTES = new HashMap<>();
    private static final Map<String, RouteBoxed> CACHED_BOXED_ROUTES = new HashMap<>();
    private static final Object LOCK = new Object();

    public static Route getCachedRoute(String routeName) {
        synchronized (LOCK) {
            log.info("Getting cached route: {}", routeName);
            return CACHED_ROUTES.get(routeName).copy();
        }
    }

    public static void putCachedRoute(String routeName, Route route) {
        synchronized (LOCK) {
            log.info("Setting cached route: {}", routeName);
            CACHED_ROUTES.put(routeName, route);
        }
    }

    public static boolean isRouteCached(String routeName) {
        synchronized (LOCK) {
            return CACHED_ROUTES.containsKey(routeName);
        }
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
