/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Yocary
 */
public interface MovimientoProjection {

    Long getId();

    Long getTreasuryId();   // m.tesoreria_id

    String getType();       // 'Ingreso' | 'Egreso'

    LocalDate getDate();    // m.fecha

    String getConcept();    // m.concepto

    String getCategory();   // c.nombre

    BigDecimal getAmount(); // m.cantidad

    String getMethod();     // mp.nombre

    String getMemberName(); // persona (opcional)

    String getNotes();      // m.observaciones
}
