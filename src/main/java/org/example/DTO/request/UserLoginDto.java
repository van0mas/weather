package org.example.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;
import org.example.config.props.AppConstants;

import static org.example.config.props.AppConstants.USERNAME_PATTERN;
import static org.example.config.props.AppConstants.USERNAME_PATTERN_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "Логин обязателен")
    @Size(
            min = AppConstants.USERNAME_MIN_LENGTH,
            max = AppConstants.USERNAME_MAX_LENGTH
    )
    @Pattern(
            regexp = USERNAME_PATTERN,
            message = USERNAME_PATTERN_MESSAGE
    )
    private String username;

    @NotBlank(message = "Пароль обязателен")
    @Size(
            min = AppConstants.PASSWORD_MIN_LENGTH,
            max = AppConstants.PASSWORD_MAX_LENGTH
    )
    private String password;
}
