package com.zanolli.backend.modules.user.service;

import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import com.zanolli.backend.modules.naipe.repository.NaipeRepository;
import com.zanolli.backend.modules.user.dto.UserCreateRequestDto;
import com.zanolli.backend.modules.user.entities.RoleEntity;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.RoleRepository;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.EmailConflictException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NaipeRepository naipeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, NaipeRepository naipeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.naipeRepository = naipeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public UserEntity createUserService(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        Optional<UserEntity> validationEmail = userRepository.findByEmail(userCreateRequestDto.email());
        
        Optional<NaipeEntity> naipeEntity = naipeRepository.findById(userCreateRequestDto.naipeId().longValue());
        
        RoleEntity roleAluno = roleRepository.findByDescription(RoleEntity.Values.ALUNO.name()).orElseThrow(() -> new RuntimeException("Role não encontrada."));

        if(validationEmail.isPresent()) {
            throw new EmailConflictException("Por favor, tente outro email.");
        }

        UserEntity user = new UserEntity();
        user.setName(userCreateRequestDto.name());
        user.setNaipe(naipeEntity.get());
        user.setDataNascimento(userCreateRequestDto.dataNascimento());
        user.setEmail(userCreateRequestDto.email());
        user.setPassword(bCryptPasswordEncoder.encode(userCreateRequestDto.password()));
        user.setRole(Set.of(roleAluno));

        return userRepository.save(user);
    }
}
