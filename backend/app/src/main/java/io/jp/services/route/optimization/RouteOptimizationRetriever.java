package io.jp.services.route.optimization;

import io.jp.core.domain.optimizedroute.OptimizedRouteBoxed;
import io.jp.database.entities.user.User;
import io.jp.database.repositories.route.OptimizedRouteRepository;
import io.jp.mapper.route.OptimizedRouteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteOptimizationRetriever {
    private final OptimizedRouteMapper optimizedRouteMapper;
    private final OptimizedRouteRepository optimizedRouteRepository;

    public List<OptimizedRouteBoxed> getOptimizedRoutesByUser(User user) {
        log.debug("Getting optimized routes connected to user {}", user.getUsername());
        return optimizedRouteRepository.findAllByUser(user).stream()
                .map(optimizedRouteMapper::mapFromJpa)
                .collect(Collectors.toList());
    }
}
