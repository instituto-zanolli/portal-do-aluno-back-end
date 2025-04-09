package com.zanolli.backend.modules.aula.entities;

import com.zanolli.backend.modules.user.entities.UserEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "inscricao")
public class InscricaoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID inscricaoId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private AulaEntity aulaEntity;

    public UUID getInscricaoId() {
        return inscricaoId;
    }

    public void setInscricaoId(UUID inscricaoId) {
        this.inscricaoId = inscricaoId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public AulaEntity getAulaEntity() {
        return aulaEntity;
    }

    public void setAulaEntity(AulaEntity aulaEntity) {
        this.aulaEntity = aulaEntity;
    }
}
