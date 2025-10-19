/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.CrearPersonaRequest;
import com.ad_samaria.dto.PersonaFichaDTO;
import com.ad_samaria.dto.PersonaMiniDTO;
import com.ad_samaria.models.Persona;
import com.ad_samaria.projections.PersonaMiniProjection;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface PersonaSvc extends CommonSvc<Persona> {

    Persona crearPersona(CrearPersonaRequest req);

    List<PersonaMiniDTO> buscarMin(String q);

    List<PersonaMiniProjection> listarPersonasMini();
    
    PersonaFichaDTO obtenerFicha(Long personaId);

}
