package io.jp.database.repositories.route;

import io.jp.database.entities.route.OptimizedRouteJpa;
import io.jp.database.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptimizedRouteRepository extends JpaRepository<OptimizedRouteJpa, Long> {
    List<OptimizedRouteJpa> findAllByUser(User user);
}
