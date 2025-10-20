/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "persona_rol_sistema", schema = "ad_samaria")
public class PersonaRolSistema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "persona_id", nullable = false)
    private Long personaId;

    @Column(name = "rol_id", nullable = false)
    private Short rolId;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "desde", nullable = false)
    private Date desde = Date.valueOf(LocalDate.now());

    @Column(name = "hasta")
    private Date hasta;

    @Column(name = "creado_en", nullable = false)
    private java.sql.Timestamp creadoEn = new java.sql.Timestamp(System.currentTimeMillis());

    public Long getId() {
        return id;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Short getRolId() {
        return rolId;
    }

    public void setRolId(Short rolId) {
        this.rolId = rolId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public java.sql.Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(java.sql.Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }
}
