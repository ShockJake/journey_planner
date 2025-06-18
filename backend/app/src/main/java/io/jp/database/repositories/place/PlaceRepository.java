package io.jp.database.repositories.place;

import io.jp.database.entities.route.PlaceJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<PlaceJpa, Long> {
    Optional<PlaceJpa> findByName(String name);
}
