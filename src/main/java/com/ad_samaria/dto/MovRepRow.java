/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Yocary
 */
public class MovRepRow {

    private String concepto;
    private String tipo;
    private String categoria;
    private BigDecimal monto;
    private LocalDate fecha;

    public MovRepRow(String concepto, String tipo, String categoria, BigDecimal monto, LocalDate fecha) {
        this.concepto = concepto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
