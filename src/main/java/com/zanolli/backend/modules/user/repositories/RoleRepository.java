package com.zanolli.backend.modules.user.repositories;

import com.zanolli.backend.modules.user.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByDescription(String description); 
}
