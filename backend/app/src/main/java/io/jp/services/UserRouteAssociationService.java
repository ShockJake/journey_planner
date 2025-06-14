package io.jp.services;

import io.jp.core.domain.OptimizedRoute;
import io.jp.core.domain.Route;
import io.jp.database.entities.user.User;
import io.jp.database.entities.user.UserRoute;
import io.jp.database.repositories.RouteRepository;
import io.jp.database.repositories.UserRouteRepository;
import io.jp.mapper.RouteJpaMapper;
import io.jp.mapper.RoutePlaceMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRouteAssociationService {
    private final UserRouteRepository userRouteRepository;
    private final RouteRepository routeRepository;
    private final RouteJpaMapper routeJpaMapper;
    private final RoutePlaceMapper routePlaceJpaMapper;

    public List<Route> getRoutesConnectedToUser(User user) {
        log.debug("Getting routes connected to user {}", user.getUsername());
        return userRouteRepository.findAllByUser(user).stream()
                .map(UserRoute::getRoute)
                .map(routeJpa ->
                        routeJpaMapper.mapFromJpa(routeJpa, routePlaceJpaMapper.mapToPlaceList(routeJpa.getPlaces())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void associateRouteWithUser(User user, String routeName) {
        log.debug("Associating route {} to user {}", routeName, user.getUsername());
        var userRoutes = userRouteRepository.findAllByUser(user)
                .stream()
                .filter(userRoute -> routeName.equals(userRoute.getRoute().getName()))
                .findFirst();
        if (userRoutes.isPresent()) {
            throw new RuntimeException("Route is already associated with user");
        }
        var route = routeRepository.findRouteJpaByName(routeName).orElseThrow(() ->
                new RuntimeException("Route %s not found".formatted(routeName)));

        userRouteRepository.save(UserRoute.builder()
                .user(user)
                .route(route)
                .build());
    }

    public void deleteAssociatedRouteOfUser(User user, String routeName) {
        var route = routeRepository.findRouteJpaByName(routeName).orElseThrow(() ->
                new RuntimeException("Route %s not found".formatted(routeName)));
        userRouteRepository.deleteByRouteAndUser(route, user);
    }

    public void deleteAssociatedRoutesOfUser(User user) {
        userRouteRepository.deleteAllByUser(user);
    }

    public void saveOptimizedRoute(User user, OptimizedRoute optimizedRoute) {

    }
}
