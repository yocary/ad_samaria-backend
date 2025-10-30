/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.math.BigDecimal;

/**
 *
 * @author Yocary
 */
public class TesoreriaRowDTO {

    private Long id;
    private String nombre;
    private Boolean estado;
    private BigDecimal ingresos;
    private BigDecimal egresos;

    public TesoreriaRowDTO(Long id, String nombre, Boolean estado,
            BigDecimal ingresos, BigDecimal egresos) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.ingresos = ingresos;
        this.egresos = egresos;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public BigDecimal getEgresos() {
        return egresos;
    }
}
