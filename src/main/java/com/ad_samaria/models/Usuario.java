/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "usuario", schema = "ad_samaria")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "persona_id", unique = true, nullable = false)
    private Long personaId;

    @Column(name = "username", unique = true, nullable = false, length = 60)
    private String username;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "rol_sistema_id", nullable = false)
    private Short rolSistemaId;

    @Column(name = "permiso_id", nullable = false)
    private Short permisoId;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "creado_en", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creadoEn;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public Short getRolSistemaId() {
        return rolSistemaId;
    }

    public void setRolSistemaId(Short rolSistemaId) {
        this.rolSistemaId = rolSistemaId;
    }

    public Short getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(Short permisoId) {
        this.permisoId = permisoId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Date creadoEn) {
        this.creadoEn = creadoEn;
    }

}
