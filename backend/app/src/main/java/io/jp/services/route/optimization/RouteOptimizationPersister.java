package io.jp.services.route.optimization;

import io.jp.core.domain.optimizedroute.OptimizedRoute;
import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.database.entities.route.OptimizedRouteJpa;
import io.jp.database.entities.user.User;
import io.jp.database.repositories.route.OptimizedRouteRepository;
import io.jp.mapper.route.OptimizedRouteMapper;
import io.jp.services.route.persistence.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteOptimizationPersister {
    private final RouteService routeService;
    private final OptimizedRouteMapper optimizedRouteMapper;
    private final OptimizedRouteRepository optimizedRouteRepository;

    public OptimizedRouteJpa saveOptimizedRoute(OptimizedRouteBoxed optimizedRoute, User user) {
        log.debug("Saving boxed optimized route {} for user {}", optimizedRoute.route().name(), user.getUsername());
        var jpaRoute = routeService.getJpaRouteByName(optimizedRoute.route().name());
        var optimizedRouteJpa = optimizedRouteMapper.mapToJpa(optimizedRoute, jpaRoute, user);
        return optimizedRouteRepository.save(optimizedRouteJpa);
    }

    public OptimizedRouteJpa saveOptimizedRoute(OptimizedRoute optimizedRoute, User user) {
        log.debug("Saving optimized route {} for user {}", optimizedRoute.route().name(), user.getUsername());
        var jpaRoute = routeService.getJpaRouteByName(optimizedRoute.route().name());
        var optimizedRouteJpa = optimizedRouteMapper.mapToJpa(optimizedRoute, jpaRoute, user);
        return optimizedRouteRepository.save(optimizedRouteJpa);
    }
}
