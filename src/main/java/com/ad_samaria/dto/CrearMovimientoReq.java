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
public class CrearMovimientoReq {

    public String tipo;            // "Ingreso" | "Egreso" (opcional si mandas tipoId)
    public Long tipoId;            // opcional si mandas 'tipo'
    public LocalDate fecha;
    public String concepto;
    public BigDecimal cantidad;
    public Long metodoPagoId;
    public Long personaId;         // opcional
    public Long categoriaId;       // opcional
    public String observaciones;
}
