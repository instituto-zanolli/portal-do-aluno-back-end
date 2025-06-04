package com.zanolli.backend.modules.evento.service;

import com.zanolli.backend.modules.evento.dto.EventoCreateRequestDto;
import com.zanolli.backend.modules.evento.entity.EventoEntity;
import com.zanolli.backend.modules.evento.repository.EventoRepository;
import com.zanolli.backend.modules.user.entities.UserEntity;
import com.zanolli.backend.modules.user.repositories.UserRepository;
import com.zanolli.backend.shared.exceptions.ImgNullException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class EventoService {

    private final UserRepository userRepository;
    private final EventoRepository eventoRepository;

    public EventoService (UserRepository userRepository, EventoRepository eventoRepository) {
        this.userRepository = userRepository;
        this.eventoRepository = eventoRepository;
    }

    @Value("${img.dir}")
    private String DIR;

    public EventoEntity createEventoService(MultipartFile file, EventoCreateRequestDto eventoCreateRequestDto, JwtAuthenticationToken jwt) {
        Optional<UserEntity> userEntity = userRepository.findById(UUID.fromString(jwt.getName()));

        if(file.isEmpty()) {
            throw new ImgNullException("Imagem não encontrada.");
        }

        try {
            int number = new Random().nextInt(10000);

            File dir = new File(this.DIR);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = this.DIR + number + "_" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            System.out.println("testeeeeeeeee " + filePath);

            EventoEntity evento = new EventoEntity();
            evento.setTitle(eventoCreateRequestDto.title());
            evento.setDescription(eventoCreateRequestDto.description());
            evento.setDate(eventoCreateRequestDto.date());
            evento.setImgEventoUrl(filePath);
            evento.setProfessor(userEntity.get());

            return eventoRepository.save(evento);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
