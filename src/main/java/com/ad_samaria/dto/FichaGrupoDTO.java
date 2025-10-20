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
public class FichaGrupoDTO {

    public Long grupoId;
    public String grupoNombre;
    public String ministerio; 

    public FichaGrupoDTO() {
    }

    public FichaGrupoDTO(Long grupoId, String grupoNombre, String ministerio) {
        this.grupoId = grupoId;
        this.grupoNombre = grupoNombre;
        this.ministerio = ministerio;
    }
}
