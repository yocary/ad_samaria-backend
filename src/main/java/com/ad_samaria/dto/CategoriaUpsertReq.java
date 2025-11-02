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
public class CategoriaUpsertReq {

    private String nombre;
    private Long tipoId;             // 1=Ingreso, 2=Egreso
    private Boolean finanzasGenerales;

    public CategoriaUpsertReq() {
    }

    public CategoriaUpsertReq(String nombre, Long tipoId, Boolean finanzasGenerales) {
        this.nombre = nombre;
        this.tipoId = tipoId;
        this.finanzasGenerales = finanzasGenerales;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public Boolean getFinanzasGenerales() {
        return finanzasGenerales;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public void setFinanzasGenerales(Boolean finanzasGenerales) {
        this.finanzasGenerales = finanzasGenerales;
    }
}
