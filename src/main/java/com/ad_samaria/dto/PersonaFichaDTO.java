/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.util.List;

/**
 *
 * @author Yocary
 */
public class PersonaFichaDTO {

    public Long id;
    public String nombreCompleto;
    public String estatus;
    public String clasificacion;
    public String tipoPersona;
    public String estadoCivil;
    public String sexo;
    public Integer edad;      
    public String telefono;
    public String dpi;
    public String direccion;
    public String ministerio;   
    public String fechaNacimiento;

    public List<FichaFamiliaDTO> familias;
    public List<FichaGrupoDTO> grupos;
    public List<FichaLiderazgoDTO> liderazgos;

    public PersonaFichaDTO() {
    }

    public PersonaFichaDTO(Long id, String nombreCompleto, String estatus, String clasificacion,
            String tipoPersona, String estadoCivil, String sexo, Integer edad, String fechaNacimiento,
            String telefono, String dpi, String direccion, String ministerio,
            List<FichaFamiliaDTO> familias, List<FichaGrupoDTO> grupos,
            List<FichaLiderazgoDTO> liderazgos) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.estatus = estatus;
        this.clasificacion = clasificacion;
        this.tipoPersona = tipoPersona;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.dpi = dpi;
        this.direccion = direccion;
        this.ministerio = ministerio;
        this.familias = familias;
        this.grupos = grupos;
        this.liderazgos = liderazgos;

    }

}
