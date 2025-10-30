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
public class DiezmoRow {

    public Long id;
    public Long personaId;
    public String nombre;     // nombre resuelto de la persona (UI-friendly)
    public BigDecimal cantidad;
    public LocalDate fecha;
    public String tipo;

    public DiezmoRow(Long id, Long personaId, String nombre, BigDecimal cantidad, LocalDate fecha, String tipo) {
        this.id = id;
        this.personaId = personaId;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.tipo = tipo;
    }
}
