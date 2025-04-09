package com.zanolli.backend.modules.aula.repositories;

import com.zanolli.backend.modules.aula.entities.InscricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<InscricaoEntity, UUID> {
}
