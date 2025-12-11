package org.example.service;

import org.example.DTO.request.UserLoginDto;
import org.example.DTO.request.UserRegisterDto;
import org.example.DTO.response.SessionResponseDto;
import org.example.exception.InvalidCredentialsException;
import org.example.exception.SessionLimitException;
import org.example.mapper.SessionResponseMapper;
import org.example.model.Session;
import org.example.model.User;
import org.example.repository.SessionRepository;
import org.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.config.props.AppConstants.MAX_ACTIVE_SESSIONS;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public void register(UserRegisterDto dto) {
        String hashedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        User user = new User(dto.getUsername(), hashedPassword);
        userRepository.save(user);
    }

    public SessionResponseDto login(UserLoginDto dto) {
        User user = authenticate(dto);

        int activeSessionsSize = sessionRepository
                .findByUserAndExpiresAtAfter(user, LocalDateTime.now()).size();

        if (activeSessionsSize >= MAX_ACTIVE_SESSIONS) {
            throw new SessionLimitException("Session limit exception");
        }

        Session session = sessionService.createSession(user);
        Session savedSession = sessionRepository.save(session);
        return SessionResponseMapper.INSTANCE.from(savedSession);
    }

    public void logout(UUID sessionId) {
        sessionRepository.deleteBySessionId(sessionId);
    }

    private User authenticate(UserLoginDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElse(null);

        if (user == null || !BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid name or password");
        }

        return user;
    }
}
