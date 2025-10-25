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
public class UsuarioItemDTO {

    private Long id;
    private Long personaId;
    private String nombreCompleto;
    private String username;
    private String rolSistema; // desde persona_rol_sistema
    private Boolean activo;

    public UsuarioItemDTO() {
    }

    public UsuarioItemDTO(Long id, Long personaId, String nombreCompleto,
            String username, String rolSistema, Boolean activo) {
        this.id = id;
        this.personaId = personaId;
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.rolSistema = rolSistema;
        this.activo = activo;
    }
    // getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolSistema() {
        return rolSistema;
    }

    public void setRolSistema(String rolSistema) {
        this.rolSistema = rolSistema;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
