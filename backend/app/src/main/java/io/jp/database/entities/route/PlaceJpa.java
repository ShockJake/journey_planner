package io.jp.database.entities.route;

import jakarta.persistence.Column;
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
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Table(name = "places")
public class PlaceJpa {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long placeId;
    @Column(nullable = false, unique = true)
    private String name;
    private Double longitude;
    private Double latitude;
    @Enumerated(STRING)
    private PlaceType type;
    @Column(name = "is_additional", columnDefinition = "TINYINT(4)")
    private boolean isAdditional;

    @ToString.Exclude
    @OneToMany(mappedBy = "place", cascade = ALL, orphanRemoval = true)
    private List<RoutePlace> places;
}
