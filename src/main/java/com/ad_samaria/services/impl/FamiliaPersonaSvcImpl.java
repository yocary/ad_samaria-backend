/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.FamiliaMiembroDTO;
import com.ad_samaria.models.FamiliaPersona;
import com.ad_samaria.repositories.FamiliaPersonaRepository;
import com.ad_samaria.repositories.FamiliaRepository;
import com.ad_samaria.repositories.RolFamiliarRepository;
import com.ad_samaria.services.FamiliaPersonaSvc;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
public class FamiliaPersonaSvcImpl extends CommonSvcImpl<FamiliaPersona, FamiliaPersonaRepository> implements FamiliaPersonaSvc {

    @Autowired
    private FamiliaRepository familiaRepo;
    @Autowired
    private RolFamiliarRepository rolRepo;

    @Override
    @Transactional(readOnly = true)
    public List<FamiliaMiembroDTO> listar(Long familiaId) {
        return repository.listarMiembros(familiaId).stream().map(r
                -> new FamiliaMiembroDTO(
                        ((Number) r[0]).longValue(),
                        ((Number) r[1]).longValue(),
                        (String) r[2],
                        (String) r[3],
                        ((Number) r[4]).shortValue()
                )
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FamiliaPersona agregar(Long familiaId, Long personaId, Short rolFamId) {
        if (!familiaRepo.existsById(familiaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Familia no encontrada");
        }
        if (!rolRepo.existsById(rolFamId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido");
        }
        FamiliaPersona fp = new FamiliaPersona();
        fp.setFamiliaId(familiaId);
        fp.setPersonaId(personaId);
        fp.setRolFamId(rolFamId);
        return repository.save(fp);
    }

    @Override
    @Transactional
    public void eliminar(Long familiaPersonaId) {
        int n = repository.eliminarVinculo(familiaPersonaId);
        if (n == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vínculo no encontrado");
        }
    }
}
