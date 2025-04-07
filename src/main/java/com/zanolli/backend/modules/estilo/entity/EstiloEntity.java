package com.zanolli.backend.modules.estilo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estilo")
public class EstiloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estilo_id")
    private Long estiloId;

    @Column(unique = true)
    private String description;

    public Long getEstiloId() {
        return estiloId;
    }

    public void setEstiloId(Long estiloId) {
        this.estiloId = estiloId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
