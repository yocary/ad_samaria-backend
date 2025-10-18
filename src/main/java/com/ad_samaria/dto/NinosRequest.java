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
public class NinosRequest {

    private String nombreMiembro;
    private String nombrePadre;
    private String nombreMadre;
    private String lugarFechaNacimiento;
    private String fechaExpedicion; 

    public String getNombreMiembro() {
        return nombreMiembro;
    }

    public void setNombreMiembro(String v) {
        this.nombreMiembro = v;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String v) {
        this.nombrePadre = v;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public void setNombreMadre(String v) {
        this.nombreMadre = v;
    }

    public String getLugarFechaNacimiento() {
        return lugarFechaNacimiento;
    }

    public void setLugarFechaNacimiento(String v) {
        this.lugarFechaNacimiento = v;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String v) {
        this.fechaExpedicion = v;
    }
}
