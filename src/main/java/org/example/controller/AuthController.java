package org.example.controller;

import org.example.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.DTO.request.UserLoginDto;
import org.example.DTO.request.UserRegisterDto;
import org.example.DTO.response.SessionResponseDto;
import org.example.annotation.AuthRequired;
import org.example.util.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.config.props.AppConstants.COOKIE_SESSION_NAME;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtils cookieUtils;

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("user") UserRegisterDto user) {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(@ModelAttribute("user") UserLoginDto user) {
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegisterDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        authService.register(dto);
        return "redirect:/auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserLoginDto dto, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        SessionResponseDto session = authService.login(dto);
        cookieUtils.setSessionCookie(session.getSessionId().toString(), response);
        return "redirect:/";
    }

    @AuthRequired
    @PostMapping("/logout")
    public String logout(@CookieValue(name = COOKIE_SESSION_NAME, required = false) String sessionId, HttpServletResponse response) {
        authService.logout(UUID.fromString(sessionId));
        cookieUtils.clearSessionCookie(response);
        return "redirect:/auth/login";
    }
}
