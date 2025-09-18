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
@Table(name = "evento", schema = "ad_samaria")
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "grupo_id", nullable = false)
    private Long grupoId;

    @Column(name = "nombre", nullable = false, length = 140)
    private String nombre;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "aviso_whatsapp", nullable = false)
    private Boolean avisoWhatsapp = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getAvisoWhatsapp() {
        return avisoWhatsapp;
    }

    public void setAvisoWhatsapp(Boolean avisoWhatsapp) {
        this.avisoWhatsapp = avisoWhatsapp;
    }

}
