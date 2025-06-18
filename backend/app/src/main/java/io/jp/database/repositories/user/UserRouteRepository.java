package io.jp.database.repositories.user;

import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.user.User;
import io.jp.database.entities.user.UserRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRouteRepository extends JpaRepository<UserRoute, Long> {
    List<UserRoute> findAllByUser(User user);
    void deleteAllByUser(User user);
    void deleteByRouteAndUser(RouteJpa route, User user);
}
