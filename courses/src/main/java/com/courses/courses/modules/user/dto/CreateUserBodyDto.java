package com.courses.courses.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserBodyDto {
    @Email
    @NotBlank
    @Schema(example = "me@email.com")
    private String email;

    @NotBlank
    @Schema(example = "myPass1234")
    private String password;
}
