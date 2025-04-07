package com.zanolli.backend.modules.auth.controller;

import com.zanolli.backend.modules.auth.dto.AuthRequest;
import com.zanolli.backend.modules.auth.dto.AuthResponse;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final JwtEncoder jwtEncoder;
    
    public AuthController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    
    // @PostMapping()
    // public ResponseEntity<AuthResponse> loginController(@RequestBody @Valid AuthRequest authRequest) {
        
    // }
}
