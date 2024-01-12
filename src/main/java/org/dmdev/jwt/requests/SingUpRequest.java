package org.dmdev.jwt.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Sing up request")
public class SingUpRequest {

    @Schema(description = "User name", example = "Dima")
    @Size(max = 100, message = "Username has to be less that 100 symbols")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "User`s email", example = "email@gmail.com")
    @Size(min = 5, max = 255, message = "Email has to be more that 5 symbols less that 100 symbols")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Wrong email format")
    private String email;

    @Schema(description = "User`s password", example = "PaSSwoRd322")
    @Size(max = 255, message = "Password has to be less that 255 symbols")
    private String password;
}
