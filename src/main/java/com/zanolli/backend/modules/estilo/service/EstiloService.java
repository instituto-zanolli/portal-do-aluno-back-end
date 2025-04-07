package com.zanolli.backend.modules.estilo.service;

import com.zanolli.backend.modules.estilo.dto.EstiloRequestDto;
import com.zanolli.backend.modules.estilo.entity.EstiloEntity;
import com.zanolli.backend.modules.estilo.repository.EstiloRepository;
import com.zanolli.backend.shared.exceptions.EstiloConflictException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class EstiloService {

    private final EstiloRepository estiloRepository;

    public EstiloService(EstiloRepository estiloRepository) {
        this.estiloRepository = estiloRepository;
    }

    public EstiloEntity registerEstiloService(@RequestBody @Valid EstiloRequestDto estiloRequestDto) {
        Optional<EstiloEntity> estilo = estiloRepository.findByDescription(estiloRequestDto.description());

        if(estilo.isPresent()) {
            throw new EstiloConflictException("Este estilo já está inserido na base de dados.");
        }

        EstiloEntity estiloEntity = new EstiloEntity();
        estiloEntity.setDescription(estiloRequestDto.description());

        return estiloRepository.save(estiloEntity);
    }

}
