package com.zanolli.backend.modules.auth.dto;

import jakarta.validation.constraints.Email;

public record AuthRequest(
        @Email
        String email,
        String password
) {
}
