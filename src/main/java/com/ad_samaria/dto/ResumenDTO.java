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
public class ResumenDTO {

    public BigDecimal totalIngresos;
    public BigDecimal totalEgresos;

    public ResumenDTO(BigDecimal totalIngresos, BigDecimal totalEgresos) {
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
    }
}
