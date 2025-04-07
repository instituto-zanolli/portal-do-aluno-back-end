package com.zanolli.backend.modules.estilo.controller;

import com.zanolli.backend.modules.estilo.dto.EstiloRequestDto;
import com.zanolli.backend.modules.estilo.dto.EstiloResponseDto;
import com.zanolli.backend.modules.estilo.entity.EstiloEntity;
import com.zanolli.backend.modules.estilo.service.EstiloService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estilo")
public class EstiloController {

    private final EstiloService estiloService;

    public EstiloController(EstiloService estiloService) {
        this.estiloService = estiloService;
    }

    @PostMapping()
    public ResponseEntity<EstiloResponseDto> registerEstiloController(@RequestBody @Valid EstiloRequestDto estiloRequestDto) {
        EstiloEntity saved = estiloService.registerEstiloService(estiloRequestDto);
        String message = saved.getDescription() + " inserido com sucesso.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new EstiloResponseDto(message));
    }
}
