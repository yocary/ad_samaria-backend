/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.projections;

import java.time.LocalDate;

/**
 *
 * @author Pablo
 */
public interface FichaLiderazgoProjection {

    Long getLiderazgoId();

    String getLiderazgo();

    String getRol();

    LocalDate getDesde();

    LocalDate getHasta();
}
