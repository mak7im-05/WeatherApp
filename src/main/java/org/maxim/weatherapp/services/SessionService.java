package org.maxim.weatherapp.services;

import org.maxim.weatherapp.entities.Session;
import org.maxim.weatherapp.entities.User;
import org.maxim.weatherapp.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public UUID create(int userId) {
        UUID sessionUuid = UUID.randomUUID();
        Session newSession = new Session(sessionUuid, userId, LocalDateTime.now().plusSeconds(40));
        sessionRepository.save(newSession);
        return sessionUuid;
    }

    public boolean isSessionActive(String sessionId) {

        UUID sessionUuid = UUID.fromString(sessionId);
        Optional<Session> session = sessionRepository.findById(sessionUuid);
        if (session.isEmpty()) return false;

        return checkSessionTime(session);
    }

    private boolean checkSessionTime(Optional<Session> session) {
        LocalDateTime now = LocalDateTime.now();
        return session.get().getExpiresAt().isAfter(now);
    }

    public void deleteSessionById(String sessionId) {
        UUID sessionUuid = UUID.fromString(sessionId);
        sessionRepository.deleteById(sessionUuid);
    }
}
