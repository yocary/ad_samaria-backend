/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "usuario", schema = "ad_samaria",
        uniqueConstraints = {
            @UniqueConstraint(name = "uq_usuario_persona", columnNames = {"persona_id"})
            ,
         @UniqueConstraint(name = "uq_usuario_username", columnNames = {"username"})
        })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "persona_id", nullable = false)
    private Long personaId;

    @Column(name = "username", nullable = false, length = 60)
    private String username;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // getters/setters
    public Long getId() {
        return id;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public String getUsername() {
        return username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
