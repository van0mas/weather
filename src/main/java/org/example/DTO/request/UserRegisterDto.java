package org.example.DTO.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.config.props.AppConstants;

import static org.example.config.props.AppConstants.USERNAME_PATTERN;
import static org.example.config.props.AppConstants.USERNAME_PATTERN_MESSAGE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = "Логин обязателен")
    @Size(
            min = AppConstants.USERNAME_MIN_LENGTH,
            max = AppConstants.USERNAME_MAX_LENGTH,
            message = "Логин должен быть от "
                    + AppConstants.USERNAME_MIN_LENGTH
                    + " до "
                    + AppConstants.USERNAME_MAX_LENGTH
                    + " символов"
    )
    @Pattern(
            regexp = USERNAME_PATTERN,
            message = USERNAME_PATTERN_MESSAGE
    )
    private String username;

    @NotBlank(message = "Пароль обязателен")
    @Size(
            min = AppConstants.PASSWORD_MIN_LENGTH,
            max = AppConstants.PASSWORD_MAX_LENGTH,
            message = "Пароль должен быть от "
                    + AppConstants.PASSWORD_MIN_LENGTH
                    + " до "
                    + AppConstants.PASSWORD_MAX_LENGTH
                    + " символов"
    )
    private String password;

    @NotBlank(message = "Подтверждение пароля обязательно")
    private String passwordConfirm;

    @AssertTrue(message = "Пароли не совпадают")
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirm);
    }
}
