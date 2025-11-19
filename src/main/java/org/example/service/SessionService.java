package org.example.service;

import org.example.model.Session;
import org.example.model.User;
import org.example.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.example.config.props.AppConstants.SESSION_LIFETIME_HOURS;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public Session createSession(User user) {
        return new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(SESSION_LIFETIME_HOURS)
        );
    }

    public Optional<Session> refreshExistingSession(User user, String currentSessionId) {

        Optional<Session> existing = getSession(currentSessionId);
        if (existing.isPresent() && existing.get().getUser().getId().equals(user.getId())) {
            Session session = existing.get();
            // Обновляем время жизни сессии
            session.setExpiresAt(LocalDateTime.now().plusHours(SESSION_LIFETIME_HOURS));
            return Optional.of(sessionRepository.save(session));
        }

        return Optional.empty();
    }

    public Optional<Session> getSession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            return Optional.empty();
        }

        try {
            UUID uuid = UUID.fromString(sessionId);
            return sessionRepository.findBySessionId(uuid)
                    .filter(s -> s.getExpiresAt().isAfter(LocalDateTime.now()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
