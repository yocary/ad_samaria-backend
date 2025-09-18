/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.io.Serializable;
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
@Table(name = "usuarios", schema = "ad_samaria")
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "numero_empleado", unique = true, length = 20)
    private String numeroEmpleado;

    @Column(nullable = false, length = 50)
    private String rol;

    @Column(nullable = false, length = 255)
    private String contrasenia;

    private Boolean estado = true;

    @Column(nullable = false, length = 50)
    private String usuario;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setId(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContraseña(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
