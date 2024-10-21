package com.courses.courses.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginDto {

    @Email
    @NotBlank
    @Schema(example = "e@email.com")
    private String email;

    @Schema(example = "1234")
    @NotBlank
    private String password;
}
