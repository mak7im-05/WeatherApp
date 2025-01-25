package org.maxim.weatherApp.services;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.entities.Session;
import org.maxim.weatherApp.repositories.SessionRepository;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public UUID create(int userId) {
        UUID sessionUuid = UUID.randomUUID();
        Session newSession = new Session(sessionUuid, userId, LocalDateTime.now().plusSeconds(60 * 60 * 24));
        sessionRepository.save(newSession);
        return sessionUuid;
    }

    public boolean isSessionActive(UUID sessionUuid) {
        Optional<Session> session = sessionRepository.findById(sessionUuid);
        if (session.isEmpty()) return false;

        return checkSessionTime(session);
    }

    private boolean checkSessionTime(Optional<Session> session) {
        return session.get().getExpiresAt().isAfter(LocalDateTime.now());
    }

    @Transactional
    public void deleteSessionById(UUID sessionUuid) {
        sessionRepository.deleteById(sessionUuid);
    }

    public int getUserIdBySessionId(UUID sessionUuid) {
        return sessionRepository.findById(sessionUuid).get().getUserId();
    }

    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void clearExpiredSessions() {
        sessionRepository.deleteExpiredSessions();
    }
}
