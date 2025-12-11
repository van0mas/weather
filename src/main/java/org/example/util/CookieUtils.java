package org.example.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.example.config.props.AppConstants.COOKIE_SESSION_MAX_AGE_HOURS;
import static org.example.config.props.AppConstants.COOKIE_SESSION_NAME;

@Component
public class CookieUtils {

    public String getSessionId(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(c -> "SESSIONID".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public void setSessionCookie(String sessionId, HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_SESSION_NAME, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_SESSION_MAX_AGE_HOURS * 60 * 60);
        response.addCookie(cookie);
    }

    public void clearSessionCookie(HttpServletResponse response) {
        setSessionCookie(null, response);
    }
}
