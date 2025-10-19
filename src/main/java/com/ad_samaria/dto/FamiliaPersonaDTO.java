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
public class FamiliaPersonaDTO {

    public Long id;
    public Long personaId;
    public String nombre;
    public String rol;

    public FamiliaPersonaDTO(Long id, Long personaId, String nombre, String rol) {
        this.id = id;
        this.personaId = personaId;
        this.nombre = nombre;
        this.rol = rol;
    }
}
