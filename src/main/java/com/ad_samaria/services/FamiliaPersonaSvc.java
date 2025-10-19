/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.FamiliaMiembroDTO;
import com.ad_samaria.models.FamiliaPersona;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface FamiliaPersonaSvc extends CommonSvc<FamiliaPersona> {

    List<FamiliaMiembroDTO> listar(Long familiaId);

    FamiliaPersona agregar(Long familiaId, Long personaId, Short rolFamId);

    void eliminar(Long familiaPersonaId);

}
