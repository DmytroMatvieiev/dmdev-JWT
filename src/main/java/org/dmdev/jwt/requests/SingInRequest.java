package org.dmdev.jwt.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Sing in request")
public class SingInRequest {

    @Schema(description = "User name", example = "Dima")
    @Size(max = 100, message = "Username has to be less that 100 symbols")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "User`s password", example = "PaSSwoRd322")
    @Size(max = 255, message = "Password has to be less that 255 symbols")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
