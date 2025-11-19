package org.example.interceptor;

import org.example.annotation.AuthRequired;
import org.example.model.Session;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod method) {
            AuthRequired authRequired = method.getMethodAnnotation(AuthRequired.class);
            if (authRequired == null) {
                return true;
            }

            String sessionId = null;
            if (request.getCookies() != null) {
                sessionId = Arrays.stream(request.getCookies())
                        .filter(c -> "SESSIONID".equals(c.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse(null);
            }

            if (sessionId == null || sessionService.getSession(sessionId).isEmpty()) {
                response.sendRedirect("/auth/login");
                return false;
            }
            Session session = sessionService.getSession(sessionId).get();
            User user = session.getUser();
            user = userRepository.findById(user.getId()).orElseThrow();
            request.setAttribute("user", user);
        }
        return true;
    }
}