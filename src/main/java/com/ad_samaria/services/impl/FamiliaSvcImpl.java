/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.models.Familia;
import com.ad_samaria.models.FamiliaPersona;
import com.ad_samaria.projections.FamiliaMiembroProjection;
import com.ad_samaria.projections.FamiliaMiniProjection;
import com.ad_samaria.repositories.FamiliaPersonaRepository;
import com.ad_samaria.repositories.FamiliaRepository;
import com.ad_samaria.services.FamiliaSvc;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
public class FamiliaSvcImpl extends CommonSvcImpl<Familia, FamiliaRepository> implements FamiliaSvc {

    @Autowired
    private FamiliaPersonaRepository famPerRepo;

    @Override
    @Transactional(readOnly = true)
    public List<FamiliaMiniProjection> listar(String q) {
        return repository.listar(q);
    }

    @Override
    @Transactional
    public Familia crear(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre requerido");
        }
        Familia f = new Familia();
        f.setNombre(nombre.trim());
        return repository.save(f);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Familia no existe");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamiliaMiembroProjection> listarMiembros(Long familiaId) {
        if (!repository.existsById(familiaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Familia no existe");
        }
        return repository.listarMiembrosList(familiaId);
    }

    @Override
    @Transactional
    public FamiliaPersona agregarMiembro(Long familiaId, Long personaId, Short rolFamId) {
        if (!repository.existsById(familiaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Familia no existe");
        }

        if (famPerRepo.existsByFamiliaIdAndPersonaId(familiaId, personaId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La persona ya pertenece a esta familia");
        }

        FamiliaPersona fp = new FamiliaPersona();
        fp.setFamiliaId(familiaId);
        fp.setPersonaId(personaId);
        fp.setRolFamId(rolFamId == null ? 3 : rolFamId); // por defecto 'Otro'
        return famPerRepo.save(fp);
    }

    @Override
    @Transactional
    public void eliminarMiembro(Long familiaPersonaId) {
        if (!famPerRepo.existsById(familiaPersonaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Miembro de familia no existe");
        }
        famPerRepo.deleteById(familiaPersonaId);
    }

    @Override
    @Transactional
    public void actualizarNombre(Long id, String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre requerido");
        }
        int n = repository.actualizarNombre(id, nombre.trim());
        if (n == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Familia no encontrada");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Familia> listarFamilia(String q) {
        List<Familia> familias = (List<Familia>) repository.findAll();
        familias.sort(Comparator.comparing(Familia::getNombre));
        return familias;
    }

    @Override
    @Transactional(readOnly = true)
    public FamiliaMiniProjection obtenerPorId(Long id) {
        return repository.obtenerPorId(id);
    }

}
