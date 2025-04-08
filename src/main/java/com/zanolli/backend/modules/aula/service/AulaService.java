package com.zanolli.backend.modules.aula.service;

import com.zanolli.backend.modules.aula.dto.AulaCreateRequestDto;
import com.zanolli.backend.modules.aula.dto.AulaResponseDto;
import com.zanolli.backend.modules.aula.entity.AulaEntity;
import com.zanolli.backend.modules.aula.repository.AulaRepository;
import com.zanolli.backend.modules.estilo.entity.EstiloEntity;
import com.zanolli.backend.modules.estilo.repository.EstiloRepository;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.EstiloConflictException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AulaService {
    
    private final AulaRepository aulaRepository;
    private final EstiloRepository estiloRepository;
    private final UserRepository userRepository;
    
    public AulaService (AulaRepository aulaRepository, EstiloRepository estiloRepository, UserRepository userRepository) {
        this.aulaRepository = aulaRepository;
        this.estiloRepository = estiloRepository;
        this.userRepository = userRepository;
    }
    
    public AulaEntity createAulaService(@RequestBody @Valid AulaCreateRequestDto aulaCreateRequestDto) {
        String name = aulaCreateRequestDto.name() + " | " + aulaCreateRequestDto.startTime();

        Optional<EstiloEntity> estiloEntity = estiloRepository.findById(aulaCreateRequestDto.estiloId());
        Optional<UserEntity> userEntity = userRepository.findById(aulaCreateRequestDto.professorId());

        if(estiloEntity.isEmpty()) {
            throw new EstiloConflictException("Este estilo não existe na base de dados.");
        }

        AulaEntity aula = new AulaEntity();
        aula.setName(name);
        aula.setDescription(aula.getDescription());
        aula.setEstilo(estiloEntity.get());
        aula.setDate(aulaCreateRequestDto.date());
        aula.setStartTime(aulaCreateRequestDto.startTime());
        aula.setEndTime(aulaCreateRequestDto.endTime());
        aula.setProfessor(userEntity.get());

        return aulaRepository.save(aula);
    }
}
