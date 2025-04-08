package com.zanolli.backend.modules.naipe.controller;

import com.zanolli.backend.modules.naipe.dto.NaipeRegisterRequestDto;
import com.zanolli.backend.modules.naipe.dto.NaipeResponseDto;
import com.zanolli.backend.modules.naipe.entity.NaipeEntity;
import com.zanolli.backend.modules.naipe.service.NaipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("naipe")
public class NaipeController {

    private final NaipeService naipeService;

    public NaipeController(NaipeService naipeService) {
        this.naipeService = naipeService;
    }

    @PostMapping()
    public ResponseEntity<NaipeResponseDto> registerNaipeController(@RequestBody @Valid NaipeRegisterRequestDto naipeRegisterRequestDto) {
        NaipeEntity saved = naipeService.registerNaipeService(naipeRegisterRequestDto);
        String message = saved.getDescription() + " inserido com sucesso.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new NaipeResponseDto(message));
    }
}
