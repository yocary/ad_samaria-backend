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
public class FamiliaMiembroDTO {

    public long id;
    public long personaId;
    public String nombre;
    public String rol;
    public Short rolFamId;

    public FamiliaMiembroDTO(long id, long personaId, String nombre, String rol, Short rolFamId) {
        this.id = id;
        this.personaId = personaId;
        this.nombre = nombre;
        this.rol = rol;
        this.rolFamId = rolFamId;
    }

}
