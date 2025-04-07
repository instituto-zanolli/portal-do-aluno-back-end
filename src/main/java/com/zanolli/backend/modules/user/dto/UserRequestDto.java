package com.zanolli.backend.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UserRequestDto(
        @NotNull(message = "Este campo é obrigatório.")
        @Size(min = 1, max = 55, message = "Este campo deve ter entre 2 e 55 caracteres.")
        String name,

        @NotNull(message = "Este campo é obrigatório.")
        Integer naipeId,

        @NotNull(message = "Este campo é obrigatório.")
        Date dataNascimento,

        @NotNull(message = "Este campo é obrigatório.")
        @Email()
        String email,

        @NotNull(message = "Este campo é obrigatório.")
        @Size(min = 8, message = "Este campo deve ter no mínimo 8 caracteres.")
        String password
) {
}
