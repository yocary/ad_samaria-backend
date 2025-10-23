/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.AsistenciaItemDTO;
import com.ad_samaria.dto.AsistenciaUpsertDTO;
import com.ad_samaria.models.Asistencia;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface AsistenciaSvc extends CommonSvc<Asistencia> {

    List<AsistenciaItemDTO> listar(Long liderazgoId, Long eventoId);

    void guardar(Long liderazgoId, Long eventoId, List<AsistenciaUpsertDTO> items);
}
