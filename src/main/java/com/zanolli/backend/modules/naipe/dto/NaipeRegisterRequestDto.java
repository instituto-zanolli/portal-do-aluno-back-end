package com.zanolli.backend.modules.naipe.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NaipeRegisterRequestDto(
        @NotNull(message = "Este campo é obrigatório.")
        @Size(min = 1, max = 55, message = "Este campo deve ter entre 2 e 55 caracteres.")
        String description
) {
}
