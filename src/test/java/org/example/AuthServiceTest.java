package org.example;

import jakarta.transaction.Transactional;
import org.example.DTO.request.UserLoginDto;
import org.example.DTO.request.UserRegisterDto;
import org.example.DTO.response.SessionResponseDto;
import org.example.model.Session;
import org.example.model.User;
import org.example.repository.SessionRepository;
import org.example.repository.UserRepository;
import org.example.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void register_createsUser() {
        UserRegisterDto dto = new UserRegisterDto("alex", "123456", "123456");
        authService.register(dto);

        Optional<User> user = userRepository.findByUsername("alex");
        assertTrue("User should be created", user.isPresent());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void register_existingUsername_throwsException() {
        authService.register(new UserRegisterDto("alex", "1111", "1111"));
        authService.register(new UserRegisterDto("alex", "2222", "2222")); // должно упасть
    }

    @Test
    public void login_createsSession() {
        authService.register(new UserRegisterDto("alex", "123456", "123456"));
        SessionResponseDto session = authService.login(new UserLoginDto("alex", "123456"), null);

        assertNotNull("Session ID should not be null", session.getSessionId());
    }

    @Test
    public void login_createsSession_withExpiration() {
        authService.register(new UserRegisterDto("alex", "123456", "123456"));
        SessionResponseDto sessionDto = authService.login(new UserLoginDto("alex", "123456"), null);

        assertNotNull(String.valueOf(sessionDto.getSessionId()), "Session ID should not be null");

        Session session = sessionRepository.findById(sessionDto.getSessionId())
                .orElseThrow(() -> new AssertionError("Session not found in DB"));

        assertNotNull(String.valueOf(session.getExpiresAt()), "Session expiration should be set");
        assertTrue("Session should not be expired immediately", session.getExpiresAt().isAfter(LocalDateTime.now()));

        Duration duration = Duration.between(LocalDateTime.now(), session.getExpiresAt());
        long minutes = duration.toMinutes();

        // Проверяем, что сессия истекает через 60 минут
        assertTrue("Session expiration should be within expected range", minutes > 0 && minutes <= 60);
    }
}


