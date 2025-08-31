package io.jp.database.entities.route;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import static jakarta.persistence.GenerationType.IDENTITY;

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
    @GeneratedValue(strategy = IDENTITY)
    private Long routeId;
    private String name;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;
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
