package org.example.DTO.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.example.config.props.AppConstants.Validation.*;
import static org.example.config.props.AppConstants.Validation.Messages.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = FIELD_REQUIRED)
    @Size(
            min = USERNAME_MIN_LENGTH,
            max = USERNAME_MAX_LENGTH,
            message = USERNAME_SIZE
    )
    @Pattern(
            regexp = USERNAME_PATTERN,
            message = USERNAME_PATTERN_MESSAGE
    )
    private String username;

    @NotBlank(message = FIELD_REQUIRED)
    @Size(
            min = PASSWORD_MIN_LENGTH,
            max = PASSWORD_MAX_LENGTH,
            message = PASSWORD_SIZE
    )
    private String password;

    @NotBlank(message = FIELD_REQUIRED)
    private String passwordConfirm;

    @AssertTrue(message = PASSWORDS_MISMATCH)
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirm);
    }
}
