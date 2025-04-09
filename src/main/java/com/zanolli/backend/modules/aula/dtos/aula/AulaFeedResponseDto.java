package com.zanolli.backend.modules.aula.dtos.aula;

import java.util.List;

public record AulaFeedResponseDto (
        List<AulaRepresentationDto> aulaRepresentationDtoList, int page, int pageSize
) {
}
