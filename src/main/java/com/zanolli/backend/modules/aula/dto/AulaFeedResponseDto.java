package com.zanolli.backend.modules.aula.dto;

import java.util.List;

public record AulaFeedResponseDto (
        List<AulaDto> aulaDtoList, int page, int pageSize
) {
}
