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
public class FichaFamiliaDTO {

    public Long familiaId;
    public String familiaNombre;
    public String rolFamiliar;

    public FichaFamiliaDTO() {
    }

    public FichaFamiliaDTO(Long familiaId, String familiaNombre, String rolFamiliar) {
        this.familiaId = familiaId;
        this.familiaNombre = familiaNombre;
        this.rolFamiliar = rolFamiliar;
    }
}
