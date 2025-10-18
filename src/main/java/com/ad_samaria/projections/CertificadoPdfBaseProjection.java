/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.projections;

import java.sql.Date;

/**
 *
 * @author Yocary
 */
public interface CertificadoPdfBaseProjection {

    Long getId();

    String getTipoNombre();

    Date getFecha();

    String getMiembroNombre();

    String getPastorNombre();
}
