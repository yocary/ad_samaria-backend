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
public class CategoriaRes {

    private Long id;
    private String nombre;
    private Long tipoId;
    private boolean finanzasGenerales;

    public CategoriaRes() {
    }

    public CategoriaRes(Long id, String nombre, Long tipoId, boolean finanzasGenerales) {
        this.id = id;
        this.nombre = nombre;
        this.tipoId = tipoId;
        this.finanzasGenerales = finanzasGenerales;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public boolean isFinanzasGenerales() {
        return finanzasGenerales;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public void setFinanzasGenerales(boolean finanzasGenerales) {
        this.finanzasGenerales = finanzasGenerales;
    }
}
