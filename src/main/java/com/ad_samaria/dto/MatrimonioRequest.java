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
public class MatrimonioRequest {

    private String esposo;
    private String esposa;
    private String fechaExpedicion;

    public String getEsposo() {
        return esposo;
    }

    public void setEsposo(String esposo) {
        this.esposo = esposo;
    }

    public String getEsposa() {
        return esposa;
    }

    public void setEsposa(String esposa) {
        this.esposa = esposa;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(String fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }
}
