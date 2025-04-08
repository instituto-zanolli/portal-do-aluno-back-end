package com.zanolli.backend.modules.auth.service;

import com.zanolli.backend.modules.auth.dto.AuthRequestDto;
import com.zanolli.backend.modules.user.entities.RoleEntity;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.EmailNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.relation.Role;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtEncoder = jwtEncoder;
    }
    
    public String loginService(@RequestBody @Valid AuthRequestDto authRequestDto) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(authRequestDto.email());
        
        if(userEntity.isEmpty() || !userEntity.get().isLoginCorrect(authRequestDto, bCryptPasswordEncoder)) {
            throw new EmailNotFoundException("email ou senha inválida.");
        }
        
        var now = Instant.now();
        var expiresIn = 10000L;

        var scope = userEntity.get().getRole()
                .stream()
                .map(RoleEntity::getDescription)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("back")
                .subject(userEntity.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();
        
        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        return  jwtValue;
    }
}
