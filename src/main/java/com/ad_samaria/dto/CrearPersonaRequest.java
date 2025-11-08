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
public class CrearPersonaRequest {

    public String nombres;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String telefono;
    public String direccion;

    public Long sexoId;             // ad_samaria.sexo.id
    public Short estadoCivilId;      // ad_samaria.estado_civil.id
    public Short clasificacionId;    // ad_samaria.clasificacion_social.id
    public Short tipoPersonaId;      // ad_samaria.tipo_persona.id

    public String fechaNacimiento;    // "yyyy-MM-dd" desde Angular
}
