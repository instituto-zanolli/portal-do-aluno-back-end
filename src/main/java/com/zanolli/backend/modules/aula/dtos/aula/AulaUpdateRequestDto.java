package com.zanolli.backend.modules.aula.dtos.aula;

import java.time.LocalTime;
import java.util.Date;

public record AulaUpdateRequestDto(
        String name,
        String description,
        Date date,
        LocalTime startTime,
        LocalTime endTime
) {
}
