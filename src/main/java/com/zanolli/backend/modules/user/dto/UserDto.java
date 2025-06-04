package com.zanolli.backend.modules.user.dto;

import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import com.zanolli.backend.modules.user.entities.RoleEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public record UserDto(
        UUID userId,
        String name,
        Optional<String> imageProfileUrl,
        NaipeEntity naipeEntity,
        Date dataNascimento,
        String email,
        Optional<Integer> peso,
        RoleEntity role
) {
}
