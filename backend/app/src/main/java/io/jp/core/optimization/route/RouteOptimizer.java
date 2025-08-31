package io.jp.core.optimization.route;

import java.time.LocalDateTime;

public interface RouteOptimizer <RouteDataType, OptimizedRouteDataType> {
    OptimizedRouteDataType optimizeRoute(RouteDataType data, LocalDateTime startDateTime);
}
