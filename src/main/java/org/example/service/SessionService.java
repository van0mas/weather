package org.example.service;

import org.example.config.props.AppConstants;
import org.example.model.Session;
import org.example.model.User;
import org.example.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public Session createSession(User user) {
        return new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(AppConstants.Session.LIFETIME_HOURS)
        );
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

    public User getUserBySession(String sessionId) {
        return getSession(sessionId)
                .map(Session::getUser)
                .flatMap(user -> userRepository.findById(user.getId()))
                .orElse(null);
    }
}
