/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import com.ad_samaria.services.impl.JsonMapConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "certificado", schema = "ad_samaria")
public class Certificado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_id", nullable = false)
    private Integer tipoId;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "observaciones", columnDefinition = "jsonb")
    @Convert(converter = JsonMapConverter.class)
    @org.hibernate.annotations.ColumnTransformer(write = "?::jsonb")
    private Map<String, Object> observaciones = new java.util.HashMap<>();

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTipoId() {
        return tipoId;
    }

    public void setTipoId(Integer tipoId) {
        this.tipoId = tipoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Map<String, Object> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Map<String, Object> observaciones) {
        this.observaciones = observaciones;
    }

}
