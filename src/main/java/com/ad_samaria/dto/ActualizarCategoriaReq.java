/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author Yocary
 */
public class ActualizarCategoriaReq {

  private String nombre;
  private Long tipoId;               // <-- Long
  private Boolean finanzasGenerales;
  private Long tesoreriaId;   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public Boolean getFinanzasGenerales() {
        return finanzasGenerales;
    }

    public void setFinanzasGenerales(Boolean finanzasGenerales) {
        this.finanzasGenerales = finanzasGenerales;
    }

    public Long getTesoreriaId() {
        return tesoreriaId;
    }

    public void setTesoreriaId(Long tesoreriaId) {
        this.tesoreriaId = tesoreriaId;
    }
  
  

}
