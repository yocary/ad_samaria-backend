/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

/**
 *
 * @author Yocary
 */
public class AsistenciaItemDTO {

    private Long personaId;
    private String nombre;
    private Boolean presente;
    private Long asistenciaId; // opcional

    public AsistenciaItemDTO() {
    }

    public AsistenciaItemDTO(Long personaId, String nombre, Boolean presente, Long asistenciaId) {
        this.personaId = personaId;
        this.nombre = nombre;
        this.presente = presente;
        this.asistenciaId = asistenciaId;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Long getAsistenciaId() {
        return asistenciaId;
    }

    public void setAsistenciaId(Long asistenciaId) {
        this.asistenciaId = asistenciaId;
    }
}
