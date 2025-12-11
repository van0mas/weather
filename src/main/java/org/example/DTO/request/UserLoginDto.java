package org.example.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;

import static org.example.config.props.AppConstants.Validation.*;
import static org.example.config.props.AppConstants.Validation.Messages.FIELD_REQUIRED;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotBlank(message = FIELD_REQUIRED)
    @Size(
            min = USERNAME_MIN_LENGTH,
            max = USERNAME_MAX_LENGTH
    )
    @Pattern(
            regexp = USERNAME_PATTERN,
            message = USERNAME_PATTERN_MESSAGE
    )
    private String username;

    @NotBlank(message = FIELD_REQUIRED)
    @Size(
            min = PASSWORD_MIN_LENGTH,
            max = PASSWORD_MAX_LENGTH
    )
    private String password;
}
