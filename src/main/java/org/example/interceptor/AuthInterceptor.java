package org.example.interceptor;

import org.example.annotation.AuthRequired;
import org.example.config.props.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod method) {
            AuthRequired authRequired = method.getMethodAnnotation(AuthRequired.class);
            if (authRequired == null) {
                return true;
            }

            if (request.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + AppConstants.Paths.AUTH + AppConstants.Paths.LOGIN);
                return false;
            }
        }

        return true;
    }
}
