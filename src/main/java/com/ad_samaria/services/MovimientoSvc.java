/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.CrearMovimientoReq;
import com.ad_samaria.dto.MovimientosGeneralesRes;
import com.ad_samaria.models.Movimiento;

/**
 *
 * @author Yocary
 */
public interface MovimientoSvc extends CommonSvc<Movimiento> {

    public Long crearDesdeDTO(Long tesoreriaId, CrearMovimientoReq dto);

    public void eliminar(Long tesoreriaId, Long movimientoId);

    public void actualizarDesdeDTO(Long tesoreriaId, Long movimientoId, CrearMovimientoReq req);

    public MovimientosGeneralesRes obtenerMovimientosGenerales(String periodo, String q, String mesISO);

}
