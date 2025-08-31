package io.jp.database.entities.route;

import io.jp.database.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "optimized_routes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OptimizedRouteJpa {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String optimizationId;

    @OneToOne(cascade = ALL)
    private RouteJpa route;
    @Column(columnDefinition = "json")
    private String placesOverrides;
    @Column(columnDefinition = "json")
    private String path;
    @Column(columnDefinition = "json")
    private String weatherInfo;
    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
