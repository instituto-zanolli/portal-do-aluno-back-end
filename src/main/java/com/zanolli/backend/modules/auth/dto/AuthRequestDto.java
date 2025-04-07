package com.zanolli.backend.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
        @Email
        @NotNull(message = "Campo obrigatório.")
        String email,

        @NotNull(message = "Campo obrigatório.")
        String password
) {
}
