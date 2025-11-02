/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "categoria", schema = "ad_samaria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "aplica_a", nullable = false)
    private Long aplicaA;

    @Column(name = "finanzas_generales", nullable = false)
    private Boolean finanzasGenerales = Boolean.FALSE;

    @ManyToOne(optional = false) // en Fase A puede ser optional=true; en Fase B pásalo a false
    @JoinColumn(name = "tesoreria_id")
    private Tesoreria tesoreria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAplicaA() {
        return aplicaA;
    }

    public void setAplicaA(Long aplicaA) {
        this.aplicaA = aplicaA;
    }

    public Boolean getFinanzasGenerales() {
        return finanzasGenerales;
    }

    public void setFinanzasGenerales(Boolean finanzasGenerales) {
        this.finanzasGenerales = finanzasGenerales;
    }

    public Tesoreria getTesoreria() {
        return tesoreria;
    }

    public void setTesoreria(Tesoreria tesoreria) {
        this.tesoreria = tesoreria;
    }

}
