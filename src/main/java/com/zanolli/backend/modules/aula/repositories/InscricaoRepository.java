package com.zanolli.backend.modules.aula.repositories;

import com.zanolli.backend.modules.aula.entities.AulaEntity;
import com.zanolli.backend.modules.aula.entities.InscricaoEntity;
import com.zanolli.backend.modules.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<InscricaoEntity, UUID> {
    boolean existsByAulaEntityAndUserEntity(AulaEntity aulaEntity, UserEntity userEntity);
}
