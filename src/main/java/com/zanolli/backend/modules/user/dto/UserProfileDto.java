package com.zanolli.backend.modules.user.dto;

import com.zanolli.backend.modules.aula.dtos.aula.AulaDto;

import java.util.List;

public record UserProfileDto(
        UserDto userDto,
        List<AulaDto> aulaDto
) {
}
