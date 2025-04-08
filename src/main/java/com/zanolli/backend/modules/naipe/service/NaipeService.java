package com.zanolli.backend.modules.naipe.service;

import com.zanolli.backend.modules.naipe.dto.NaipeRegisterRequestDto;
import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import com.zanolli.backend.modules.naipe.repository.NaipeRepository;
import com.zanolli.backend.shared.exceptions.NaipeConflictException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class NaipeService {

    private final NaipeRepository naipeRepository;

    public NaipeService(NaipeRepository naipeRepository) {
        this.naipeRepository = naipeRepository;
    }

    public NaipeEntity registerNaipeService(@RequestBody @Valid NaipeRegisterRequestDto naipeRegisterRequestDto) {
        Optional<NaipeEntity> descriptionValidation = naipeRepository.findByDescription((naipeRegisterRequestDto.description()));

        if(descriptionValidation.isPresent()) {
            throw new NaipeConflictException("Este naipe já está inserido na base de dados.");
        }

        NaipeEntity entity = new NaipeEntity();
        entity.setDescription(naipeRegisterRequestDto.description());

        return naipeRepository.save(entity);
    }
}
