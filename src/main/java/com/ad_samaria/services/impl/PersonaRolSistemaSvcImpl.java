/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.PersonaRolDTO;
import com.ad_samaria.models.PersonaRolSistema;
import com.ad_samaria.repositories.PersonaRolSistemaRepository;
import com.ad_samaria.services.PersonaRolSistemaSvc;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
public class PersonaRolSistemaSvcImpl extends CommonSvcImpl<PersonaRolSistema, PersonaRolSistemaRepository> implements PersonaRolSistemaSvc {

    @Override
    @Transactional
    public PersonaRolSistema asignar(Long personaId, Short rolId) {
        if (repository.existsByPersonaIdAndRolIdAndActivoTrue(personaId, rolId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La persona ya tiene este rol activo");
        }
        PersonaRolSistema prs = new PersonaRolSistema();
        prs.setPersonaId(personaId);
        prs.setRolId(rolId);
        prs.setActivo(true);
        return repository.save(prs);
    }

    @Override
    @Transactional
    public void quitar(Long personaRolId) {
        int updated = repository.desactivar(personaRolId);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asignación no encontrada o ya inactiva");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaRolDTO> listarPorPersona(Long personaId) {
        List<Object[]> rows = repository.listarActivosPorPersona(personaId);
        List<PersonaRolDTO> list = new ArrayList<>();
        for (Object[] r : rows) {
            Long id = ((Number) r[0]).longValue();
            Short rolId = ((Number) r[1]).shortValue();
            String rolNombre = (String) r[2];
            String desde = (String) r[3];
            String hasta = (String) r[4];
            list.add(new PersonaRolDTO(id, rolId, rolNombre, desde, hasta));
        }
        return list;
    }
}
