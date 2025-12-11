package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.service.SessionService;
import org.example.util.CookieUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class UserContextInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private final CookieUtils cookieUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String sessionId = cookieUtils.getSessionId(request);
        User user = sessionService.getUserBySession(sessionId);

        if (user != null) {
            request.setAttribute("user", user);
        }
        return true;
    }
}
