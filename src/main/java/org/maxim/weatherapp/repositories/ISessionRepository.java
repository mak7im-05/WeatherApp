package org.maxim.weatherapp.repositories;

import org.maxim.weatherapp.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISessionRepository extends JpaRepository<Session, UUID> {
}


