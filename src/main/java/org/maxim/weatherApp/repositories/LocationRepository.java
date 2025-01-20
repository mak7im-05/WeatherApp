package org.maxim.weatherApp.repositories;

import org.maxim.weatherApp.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByUserId(int userId);

    Optional<Location> findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
