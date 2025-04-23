package io.jp.database.entities.route;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "routes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RouteJpa {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long routeId;
    private String name;
    private String municipality;
    private String description;
    private String imageUrl;

    @Enumerated(STRING)
    private RouteType routeType;

    @ToString.Exclude
    @OneToMany(mappedBy = "route", cascade = ALL, orphanRemoval = true)
    private List<RoutePlace> places;
    private Double centerLatitude;
    private Double centerLongitude;
}
