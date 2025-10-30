/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.MetodoPagoDto;
import com.ad_samaria.models.MetodoPago;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface MetodoPagoSvc extends CommonSvc<MetodoPago> {

    public List<MetodoPagoDto> listarActivos();
}
