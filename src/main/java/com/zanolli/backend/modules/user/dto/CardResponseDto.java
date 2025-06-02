package com.zanolli.backend.modules.user.dto;

import com.zanolli.backend.modules.naipe.entity.NaipeEntity;

import java.util.Date;
import java.util.Optional;

public record CardResponseDto(
    String name,
    String naipeDescription,
    Date dataNascimento,
    String email,
    Optional<Integer> peso,
    String imageProfileUrl
) {
}
