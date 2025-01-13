package org.maxim.weatherapp.repositories;

import org.maxim.weatherapp.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByUserId(int userId);

    void deleteByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
