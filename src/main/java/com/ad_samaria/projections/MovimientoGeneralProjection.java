/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.projections;

import java.math.BigDecimal;

/**
 *
 * @author Yocary
 */
public interface MovimientoGeneralProjection {

    Long getTesoreriaId();

    String getTesoreria();

    Long getCategoriaId();

    String getCategoria();

    String getTipo();

    String getFecha();

    BigDecimal getAmount();

}
