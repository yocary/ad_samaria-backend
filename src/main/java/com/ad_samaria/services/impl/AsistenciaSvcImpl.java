/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.AsistenciaItemDTO;
import com.ad_samaria.dto.AsistenciaUpsertDTO;
import com.ad_samaria.models.Asistencia;
import com.ad_samaria.repositories.AsistenciaRepository;
import com.ad_samaria.services.AsistenciaSvc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Service
public class AsistenciaSvcImpl extends CommonSvcImpl<Asistencia, AsistenciaRepository> implements AsistenciaSvc {

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaItemDTO> listar(Long liderazgoId, Long eventoId) {
        if (liderazgoId == null || eventoId == null) {
            throw new EntityNotFoundException("Parámetros inválidos");
        }
        List<Object[]> rows = repository.listarAsistencia(liderazgoId, eventoId);
        List<AsistenciaItemDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long personaId = r[0] == null ? null : ((Number) r[0]).longValue();
            String nombre = (String) r[1];
            Boolean presente = r[2] == null ? Boolean.FALSE : (Boolean) r[2];
            Long asistenciaId = r[3] == null ? null : ((Number) r[3]).longValue();
            out.add(new AsistenciaItemDTO(personaId, nombre, presente, asistenciaId));
        }
        return out;
    }

    @Override
    @Transactional
    public void guardar(Long liderazgoId, Long eventoId, List<AsistenciaUpsertDTO> items) {
        if (items == null) {
            return;
        }
        for (AsistenciaUpsertDTO it : items) {
            repository.upsert(eventoId, it.getPersonaId(), Boolean.TRUE.equals(it.getPresente()), it.getObservacion());
        }
    }
}
