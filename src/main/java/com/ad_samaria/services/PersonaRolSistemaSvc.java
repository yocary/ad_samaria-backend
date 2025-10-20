/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.PersonaRolDTO;
import com.ad_samaria.models.PersonaRolSistema;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface PersonaRolSistemaSvc extends CommonSvc<PersonaRolSistema>  {

    PersonaRolSistema asignar(Long personaId, Short rolId);

    void quitar(Long personaRolId);

    List<PersonaRolDTO> listarPorPersona(Long personaId);
}
