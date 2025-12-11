package org.example.service.scheduler;

import org.example.config.props.AppConstants;
import org.example.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionCleanupService {

    private final SessionRepository sessionRepository;

    @Scheduled(fixedRate = AppConstants.Session.SCHEDULER_INTERVAL_MINUTES * 60 * 1000)
    @Transactional
    public void deleteExpiredSessions() {
        log.info("Deleting expired sessions...");
        sessionRepository.deleteExpiredSessions(LocalDateTime.now());
        log.info("Session cleanup completed");
    }
}

