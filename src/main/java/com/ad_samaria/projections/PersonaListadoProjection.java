/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.projections;

/**
 *
 * @author Yocary
 */
public interface PersonaListadoProjection {

    Long getId();

    String getNombres();

    String getApellidoPaterno();

    String getApellidoMaterno();

    String getTelefono();

    String getDpi();

    String getFechaNacimiento();

    String getSexo();

    String getEstadoCivil();

    String getEstatus();

    String getMinisterio();
}
