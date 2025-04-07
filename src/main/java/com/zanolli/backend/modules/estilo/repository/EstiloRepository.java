package com.zanolli.backend.modules.estilo.repository;

import com.zanolli.backend.modules.estilo.entity.EstiloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstiloRepository extends JpaRepository<EstiloEntity, Long> {
    Optional<EstiloEntity> findByDescription(String description);
}
