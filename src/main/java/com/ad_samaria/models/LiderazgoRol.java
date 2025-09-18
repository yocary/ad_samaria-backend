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
import javax.persistence.Table;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "liderazgo_rol", schema = "ad_samaria")
public class LiderazgoRol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "liderazgo_id", nullable = false)
    private Long liderazgoId;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLiderazgoId() {
        return liderazgoId;
    }

    public void setLiderazgoId(Long liderazgoId) {
        this.liderazgoId = liderazgoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
