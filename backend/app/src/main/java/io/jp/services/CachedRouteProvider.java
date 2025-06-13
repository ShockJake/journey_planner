package io.jp.services;

import io.jp.core.domain.Route;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CachedRouteProvider {
    private static final Map<String, Route> CACHED_ROUTES = new HashMap<>();
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
}
