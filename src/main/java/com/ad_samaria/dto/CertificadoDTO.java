/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.time.LocalDate;

/**
 *
 * @author Yocary
 */
public class CertificadoDTO {

    private Long id;
    private String miembro;
    private String tipo;
    private LocalDate fecha;

    public CertificadoDTO(Long id, String miembro, String tipo, LocalDate fecha) {
        this.id = id;
        this.miembro = miembro;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public String getMiembro() {
        return miembro;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
