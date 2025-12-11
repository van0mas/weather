package org.example.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.props.AppConstants;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
        Cookie cookie = new Cookie(AppConstants.Cookie.SESSION_NAME, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(AppConstants.Cookie.SESSION_MAX_AGE_HOURS * 60 * 60);
        response.addCookie(cookie);
    }

    public void clearSessionCookie(HttpServletResponse response) {
        setSessionCookie(null, response);
    }
}
