package com.zanolli.backend.modules.evento.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Date;

public record EventoCreateRequestDto(
        @NotNull(message = "Campo obrigatório.")
        String title,

        @NotNull(message = "Campo obrigatório.")
        String description,

        @NotNull(message = "Campo obrigatório.")
        Date date
) {
}
