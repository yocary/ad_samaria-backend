/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.ActualizarTesoreriaReq;
import com.ad_samaria.dto.MovimientoRowDTO;
import com.ad_samaria.dto.ResumenDTO;
import com.ad_samaria.dto.TesoreriaRowDTO;
import com.ad_samaria.models.Tesoreria;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface TesoreriaSvc extends CommonSvc<Tesoreria> {

    public Tesoreria crearTesoreria(String nombre, Boolean estado);

    public List<TesoreriaRowDTO> listar(String estado, String q, String periodo);

    public List<MovimientoRowDTO> listarMovimientos(Long tesoreriaId, String periodo, String q);

    public ResumenDTO resumen(Long tesoreriaId, String periodo);

    public void actualizarTesoreria(Long id, ActualizarTesoreriaReq req);
    
    public void eliminarTesoreria(Long id);

}
