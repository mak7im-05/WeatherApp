package org.maxim.weatherApp.service;

import lombok.RequiredArgsConstructor;
import org.maxim.weatherApp.entity.Session;
import org.maxim.weatherApp.repository.SessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public UUID create(int userId) {
        UUID sessionUuid = UUID.randomUUID();
        Session newSession = new Session(sessionUuid, userId, LocalDateTime.now().plusSeconds(60 * 60 * 24));
        sessionRepository.save(newSession);
        return sessionUuid;
    }

    @Transactional(readOnly = true)
    public boolean isSessionActive(UUID sessionUuid) {
        return sessionRepository.findById(sessionUuid).filter(
                session -> session.getExpiresAt().isAfter(LocalDateTime.now())
        ).isPresent();
    }

    @Transactional
    public void deleteSessionById(UUID sessionUuid) {
        sessionRepository.deleteById(sessionUuid);
    }

    @Transactional(readOnly = true)
    public int getUserIdBySessionId(UUID sessionUuid) {
        return sessionRepository.findById(sessionUuid)
                .orElseThrow(() -> new IllegalStateException("Session not found"))
                .getUserId();
    }

    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void clearExpiredSessions() {
        sessionRepository.deleteExpiredSessions();
    }
}
