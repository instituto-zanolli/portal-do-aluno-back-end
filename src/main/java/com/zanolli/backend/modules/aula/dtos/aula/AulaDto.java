package com.zanolli.backend.modules.aula.dtos.aula;

import com.zanolli.backend.modules.estilo.entity.EstiloEntity;

import java.time.LocalTime;
import java.util.Date;

public record AulaDto(
        String name,
        String description,
        EstiloEntity estilo,
        Date date,
        LocalTime startTime,
        LocalTime endTime
) {
}
