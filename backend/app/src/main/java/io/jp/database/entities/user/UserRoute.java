package io.jp.database.entities.user;


import io.jp.database.entities.route.RouteJpa;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRoute {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteJpa route;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
