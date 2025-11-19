package org.example.controller;

import org.example.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.DTO.request.UserLoginDto;
import org.example.DTO.request.UserRegisterDto;
import org.example.DTO.response.SessionResponseDto;
import org.example.annotation.AuthRequired;
import org.example.config.props.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.config.props.AppConstants.COOKIE_SESSION_MAX_AGE_HOURS;
import static org.example.config.props.AppConstants.COOKIE_SESSION_NAME;

@RequiredArgsConstructor
@Controller
@RequestMapping(AppConstants.Paths.AUTH)
public class AuthController {

    private final AuthService authService;

    @GetMapping(AppConstants.Paths.REGISTER)
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegisterDto());
        return AppConstants.Templates.REGISTER;
    }

    @PostMapping(AppConstants.Paths.REGISTER)
    public String register(@Valid @ModelAttribute("user") UserRegisterDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return AppConstants.Templates.REGISTER;
        }

        authService.register(dto);
        return AppConstants.Redirects.AUTH_LOGIN;
    }

    @GetMapping(AppConstants.Paths.LOGIN)
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserLoginDto());
        return AppConstants.Templates.LOGIN;
    }

    @PostMapping(AppConstants.Paths.LOGIN)
    public String login(@Valid @ModelAttribute("user") UserLoginDto dto,
                        BindingResult bindingResult,
                        @CookieValue(name = COOKIE_SESSION_NAME, required = false) String sessionId,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return AppConstants.Templates.LOGIN;
        }

        SessionResponseDto session = authService.login(dto, sessionId);
        setSessionCookie(
                session.getSessionId().toString(),
                COOKIE_SESSION_MAX_AGE_HOURS * 60 * 60,
                response
        );

        return AppConstants.Redirects.HOME;
    }

    @AuthRequired
    @PostMapping(AppConstants.Paths.LOGOUT)
    public String logout(@CookieValue(name = COOKIE_SESSION_NAME, required = false) String sessionId, HttpServletResponse response) {
        authService.logout(UUID.fromString(sessionId));
        setSessionCookie(null, 0, response);

        return AppConstants.Redirects.AUTH_LOGIN;
    }

    private void setSessionCookie(String sessionId, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_SESSION_NAME, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
