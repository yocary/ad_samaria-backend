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

    public String nombre;
    public Long tipoMovimientoId;
    
    @JsonAlias({"finanzas_generales", "finanzasGenerales"})
    public Boolean finanzasGenerales;

}
