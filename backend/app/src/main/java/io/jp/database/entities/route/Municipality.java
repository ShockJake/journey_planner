package io.jp.database.entities.route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Municipality {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double latitude;
    private Double longitude;
}
