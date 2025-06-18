package io.jp.database.repositories.place;

import io.jp.database.entities.route.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
}
