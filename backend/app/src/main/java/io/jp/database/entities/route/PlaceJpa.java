package io.jp.database.entities.route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class PlaceJpa {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long placeId;
    @Column(nullable = false, unique = true)
    private String name;
    private Double longitude;
    private Double latitude;
    @Enumerated(STRING)
    private PlaceType type;

    @ToString.Exclude
    @OneToMany(mappedBy = "place", cascade = ALL, orphanRemoval = true)
    private List<RoutePlace> places;
}
