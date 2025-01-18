package org.maxim.weatherApp.services;

import org.maxim.weatherApp.entities.Session;
import org.maxim.weatherApp.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
        Session newSession = new Session(sessionUuid, userId, LocalDateTime.now().plusSeconds(60 * 60 * 24));

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

    public int getUserIdBySessionId(String sessionId) {
        UUID sessionUuid = UUID.fromString(sessionId);
        return sessionRepository.findById(sessionUuid).get().getUserId();
    }

    @Scheduled(fixedRate = 60 * 60 * 100)
    public void clearExpiredSessions() {
        sessionRepository.deleteExpiredSessions();
        System.out.println("CLEAR");
    }
}
