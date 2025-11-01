/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.util.List;

/**
 *
 * @author Yocary
 */
public class MovimientosGeneralesRes {

    public List<MovimientosGeneralesItem> items;
    public MovimientosGeneralesTotales totales;

    public MovimientosGeneralesRes(List<MovimientosGeneralesItem> items, MovimientosGeneralesTotales totales) {
        this.items = items;
        this.totales = totales;
    }
}
