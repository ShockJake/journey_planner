package io.jp.database.repositories;

import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.route.RouteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<RouteJpa, Long> {
    List<RouteJpa> findAllByRouteType(RouteType routeType);
    Optional<RouteJpa> findRouteJpaByName(String name);
}
