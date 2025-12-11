package org.example.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.example.DTO.request.UserLoginDto;
import org.example.DTO.request.UserRegisterDto;
import org.example.config.props.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.InvalidCredentialsException;
import org.example.exception.SessionLimitException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Ошибки целостности данных — бизнес-ошибки (дубликаты и нарушение ограничений БД)
     * Возвращаем пользователю сообщения.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityException(DataIntegrityViolationException e, Model model) {
        String message = "Ошибка целостности данных";

        for (Throwable t = e; t != null; t = t.getCause()) {
            String msg = t.getMessage();
            if (msg == null) continue;

            if (msg.contains("users_username_key")) {
                log.warn("Duplicate username detected: {}", e.getMessage());
                return prepareErrorModel(model, "Пользователь с таким логином уже существует", new UserRegisterDto());
            }

            if (msg.contains("unique_user_location")) {
                log.info("Attempt to add duplicate location — ignored: {}", e.getMessage());
                return AppConstants.Redirects.HOME;
            }
        }

        log.warn("Unexpected DataIntegrityViolationException: {}", e.getMessage());
        return prepareErrorModel(model, message, new UserRegisterDto());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentialsException(InvalidCredentialsException e, Model model) {
        log.warn("Invalid login attempt: {}", e.getMessage());
        return prepareErrorModel(model, "Неправильные логин или пароль", new UserLoginDto());
    }

    @ExceptionHandler(SessionLimitException.class)
    public String handleSessionLimitException(SessionLimitException e, Model model) {
        log.warn("Session limit reached: {}", e.getMessage());
        return prepareErrorModel(model, "Достигнут лимит активных сессий. Выйдите на другом устройстве.", new UserLoginDto());
    }

    /**
     * Ошибки HTTP клиента — случай "внешний сервис недоступен".
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public String handleHttpClientError(HttpClientErrorException ex) {
        log.error("HTTP error when calling external API: {}", ex.getMessage(), ex);
        return AppConstants.Redirects.HOME;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(HttpServletRequest request, Exception ex) {
        return "redirect:/";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException ex, Model model) {
        model.addAttribute("error", "Страница не найдена");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handle500(Exception ex, Model model) {
        model.addAttribute("error", "Внутренняя ошибка сервера");
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }

    private String prepareErrorModel(Model model, String message, Object userDto) {
        model.addAttribute("error", message);
        model.addAttribute("user", userDto);
        return (userDto instanceof UserLoginDto) ? "login" : "register";
    }
}
