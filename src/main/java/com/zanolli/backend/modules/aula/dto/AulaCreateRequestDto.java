package com.zanolli.backend.modules.aula.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public record AulaCreateRequestDto(
        @NotNull(message = "Campo obrigatório.")
        String name,

        @NotNull(message = "Campo obrigatório.")
        String description,

        @NotNull(message = "Campo obrigatório.")
        Long estiloId,

        @NotNull(message = "Campo obrigatório.")
        Date date,

        @NotNull(message = "Campo obrigatório.")
        LocalTime startTime,

        @NotNull(message = "Campo obrigatório.")
        LocalTime endTime,

        @NotNull(message = "Campo obrigatório.")
        UUID professorId
) {
}
