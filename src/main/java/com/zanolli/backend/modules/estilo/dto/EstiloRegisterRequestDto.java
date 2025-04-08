package com.zanolli.backend.modules.estilo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstiloRegisterRequestDto(
        @NotNull(message = "Este campo é obrigatório.")
        @Size(min = 1, max = 55, message = "Este campo deve ter entre 2 e 55 caracteres.")
        String description
) {
}
