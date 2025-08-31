package io.jp.services.configuration;

import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.core.domain.route.Route;
import io.jp.core.optimization.route.RouteOptimizer;
import io.jp.core.optimization.route.RouteOptimizerNoOpt;
import io.jp.services.route.optimization.RouteOptimizationService;
import io.jp.services.route.optimization.RouteOptimizationServiceNoOpt;
import io.jp.services.route.optimization.RouteOptimizationServiceOpt;
import io.jp.services.route.persistence.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RouteOptimizationConfiguration {

    @Bean
    @ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "opt")
    public RouteOptimizationService<OptimizedRoute> routeOptimizationServiceOpt(RouteService routeService, RouteOptimizer<Route, OptimizedRoute> routeOptimizer) {
        log.info("Route optimization is used with optimization");
        return new RouteOptimizationServiceOpt(routeOptimizer, routeService);
    }

    @Bean
    @ConditionalOnProperty(name = "service.implementation.route.optimization", havingValue = "no-opt")
    public RouteOptimizationService<OptimizedRouteBoxed> routeOptimizationServiceNoOpt(RouteService routeService, RouteOptimizerNoOpt routeOptimizer) {
        log.info("Route optimization is used without optimization");
        return new RouteOptimizationServiceNoOpt(routeService, routeOptimizer);
    }
}
