package org.maxim.weatherapp.services;

import org.maxim.weatherapp.entities.Session;
import org.maxim.weatherapp.repositories.ISessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final ISessionRepository ISessionRepository;

    @Autowired
    public SessionService(ISessionRepository ISessionRepository) {
        this.ISessionRepository = ISessionRepository;
    }

    public UUID create(int userId) {
        UUID sessionUuid = UUID.randomUUID();
        Session newSession = new Session(sessionUuid, userId, LocalDateTime.now().plusSeconds(60*60*24));

        ISessionRepository.save(newSession);
        return sessionUuid;
    }

    public boolean isSessionActive(String sessionId) {
        UUID sessionUuid = UUID.fromString(sessionId);
        Optional<Session> session = ISessionRepository.findById(sessionUuid);
        if (session.isEmpty()) return false;

        return checkSessionTime(session);
    }

    private boolean checkSessionTime(Optional<Session> session) {
        LocalDateTime now = LocalDateTime.now();
        return session.get().getExpiresAt().isAfter(now);
    }

    public void deleteSessionById(String sessionId) {
        UUID sessionUuid = UUID.fromString(sessionId);
        ISessionRepository.deleteById(sessionUuid);
    }

    public int getUserIdBySessionId(String sessionId) {
        UUID sessionUuid = UUID.fromString(sessionId);
        return ISessionRepository.findById(sessionUuid).get().getUserId();
    }
}
