package io.jp.mapper;

import io.jp.database.entities.route.RouteJpa;
import io.jp.database.entities.user.User;
import io.jp.database.entities.user.UserRoute;
import org.springframework.stereotype.Component;

@Component
public class UserRouteMapper {

    public UserRoute mapToUserRoute(User user, RouteJpa route) {
        return UserRoute.builder()
                .user(user)
                .route(route)
                .build();
    }
}
