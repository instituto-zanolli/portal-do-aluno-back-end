package com.zanolli.backend.modules.aula.repositories;

import com.zanolli.backend.modules.aula.entities.AulaEntity;
import com.zanolli.backend.modules.aula.entities.InscricaoEntity;
import com.zanolli.backend.modules.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<InscricaoEntity, UUID> {
    boolean existsByAulaEntityAndUserEntity(AulaEntity aulaEntity, UserEntity userEntity);

    @Transactional
    @Modifying
    @Query("DELETE FROM InscricaoEntity i WHERE i.aulaEntity.id = :aulaId")
    void deleteByAulaId(@Param("aulaId") UUID aulaId);
}
