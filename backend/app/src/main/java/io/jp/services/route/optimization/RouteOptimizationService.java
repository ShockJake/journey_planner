package io.jp.services.route.optimization;

import io.jp.api.dto.RouteOptimizationRequest;

public interface RouteOptimizationService <OptimizedRouteType> {

    OptimizedRouteType optimizeRoute(RouteOptimizationRequest request);
}

