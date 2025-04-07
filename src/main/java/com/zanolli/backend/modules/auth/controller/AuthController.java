package com.zanolli.backend.modules.auth.controller;

import com.zanolli.backend.modules.auth.dto.AuthRequestDto;
import com.zanolli.backend.modules.auth.dto.AuthResponseDto;
import com.zanolli.backend.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

     @PostMapping("/login")
     public ResponseEntity<AuthResponseDto> loginController(@RequestBody @Valid AuthRequestDto authRequestDto) {
        String acessToken = authService.loginService(authRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AuthResponseDto(acessToken));
     }
}
