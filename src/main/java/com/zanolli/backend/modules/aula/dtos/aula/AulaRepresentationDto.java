package com.zanolli.backend.modules.aula.dtos.aula;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalTime;
import java.util.Date;

public class AulaRepresentationDto extends RepresentationModel<AulaRepresentationDto> {
    private String name;
    private String description;
    private String estiloDescription;
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;

    public AulaRepresentationDto(String name, String description, String estiloDescription, Date date, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.description = description;
        this.estiloDescription = estiloDescription;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstiloDescription() {
        return estiloDescription;
    }

    public void setEstiloDescription(String estiloDescription) {
        this.estiloDescription = estiloDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}