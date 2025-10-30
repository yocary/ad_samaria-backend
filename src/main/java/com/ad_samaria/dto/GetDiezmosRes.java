/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Yocary
 */
public class GetDiezmosRes {
    public List<DiezmoRow> items;
    public Totales totales;

    public static class Totales {
        public BigDecimal ingresos;
        public BigDecimal egresos;

        public Totales() {}
        public Totales(BigDecimal ingresos, BigDecimal egresos) {
            this.ingresos = ingresos; this.egresos = egresos;
        }
    }

    public GetDiezmosRes(List<DiezmoRow> items, Totales totales) {
        this.items = items; this.totales = totales;
    }
}