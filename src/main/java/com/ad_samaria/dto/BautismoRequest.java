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
public class BautismoRequest {

    private String nombreMiembro;
    private String fechaBautismo;  
    private String fechaExpedicion;  

    public String getNombreMiembro() {
        return nombreMiembro;
    }

    public void setNombreMiembro(String v) {
        this.nombreMiembro = v;
    }

    public String getFechaBautismo() {
        return fechaBautismo;
    }

    public void setFechaBautismo(String v) {
        this.fechaBautismo = v;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String v) {
        this.fechaExpedicion = v;
    }
}
