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
public class CrearDiezmoReq {

    public String tipo;       // "Ingreso" | "Egreso"
    public Long personaId;  // ⬅️ ahora enviamos el ID de persona
    public BigDecimal cantidad;
    public LocalDate fecha;
}
