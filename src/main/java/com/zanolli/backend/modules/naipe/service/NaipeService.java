package com.zanolli.backend.modules.naipe.service;

import com.zanolli.backend.modules.naipe.dto.NaipeRequestDto;
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

    public NaipeEntity registerNaipeService (@RequestBody @Valid NaipeRequestDto naipeRequestDto) {
        Optional<NaipeEntity> descriptionValidation = naipeRepository.findByDescription((naipeRequestDto.description()));

        if(descriptionValidation.isPresent()) {
            throw new NaipeConflictException("Este Naipe já está inserido na base de dados.");
        }

        NaipeEntity entity = new NaipeEntity();
        entity.setDescription(naipeRequestDto.description());

        return naipeRepository.save(entity);
    }
}
