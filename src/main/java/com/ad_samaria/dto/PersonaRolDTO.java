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
public class PersonaRolDTO {

    public Long id;
    public Short rolId;
    public String rolNombre;
    public String desde;
    public String hasta;

    public PersonaRolDTO(Long id, Short rolId, String rolNombre, String desde, String hasta) {
        this.id = id;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
        this.desde = desde;
        this.hasta = hasta;

    }
}