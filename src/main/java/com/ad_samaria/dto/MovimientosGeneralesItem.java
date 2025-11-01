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
public class MovimientosGeneralesItem {

    public Long tesoreriaId;
    public String tesoreria;
    public Long categoriaId;
    public String categoria;
    public String tipo; // "Ingreso"|"Egreso"
    public BigDecimal amount;

    public MovimientosGeneralesItem(Long tesoreriaId, String tesoreria, Long categoriaId, String categoria, String tipo, BigDecimal amount) {
        this.tesoreriaId = tesoreriaId;
        this.tesoreria = tesoreria;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.tipo = tipo;
        this.amount = amount;
    }
}
