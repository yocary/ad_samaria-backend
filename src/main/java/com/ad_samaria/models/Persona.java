/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "persona", schema = "ad_samaria")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombres", nullable = false, length = 80)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 80)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 80)
    private String apellidoMaterno;

    @Column(name = "telefono", length = 25)
    private String telefono;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "sexo_id")
    private Short sexoId;

    @Column(name = "estado_civil_id")
    private Short estadoCivilId;

    @Column(name = "clasif_social_id")
    private Short clasifSocialId;

    @Column(name = "tipo_persona_id")
    private Short tipoPersonaId;

    @Column(name = "estatus_id")
    private Short estatusId;

    @Column(name = "ministerio_id")
    private Short ministerioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Short getSexoId() {
        return sexoId;
    }

    public void setSexoId(Short sexoId) {
        this.sexoId = sexoId;
    }

    public Short getEstadoCivilId() {
        return estadoCivilId;
    }

    public void setEstadoCivilId(Short estadoCivilId) {
        this.estadoCivilId = estadoCivilId;
    }

    public Short getClasifSocialId() {
        return clasifSocialId;
    }

    public void setClasifSocialId(Short clasifSocialId) {
        this.clasifSocialId = clasifSocialId;
    }

    public Short getTipoPersonaId() {
        return tipoPersonaId;
    }

    public void setTipoPersonaId(Short tipoPersonaId) {
        this.tipoPersonaId = tipoPersonaId;
    }

    public Short getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Short estatusId) {
        this.estatusId = estatusId;
    }

    public Short getMinisterioId() {
        return ministerioId;
    }

    public void setMinisterioId(Short ministerioId) {
        this.ministerioId = ministerioId;
    }

}
