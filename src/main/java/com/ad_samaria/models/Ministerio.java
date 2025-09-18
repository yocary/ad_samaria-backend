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
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "ministerio", schema = "ad_samaria")
public class Ministerio implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "nombre", unique = true, nullable = false, length = 60)
    private String nombre;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
