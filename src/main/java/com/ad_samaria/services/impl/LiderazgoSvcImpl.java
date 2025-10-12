/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.LiderazgoListadoDTO;
import com.ad_samaria.dto.LiderazgoMiembroDTO;
import com.ad_samaria.models.Liderazgo;
import com.ad_samaria.repositories.LiderazgoMiembroRepository;
import com.ad_samaria.repositories.LiderazgoRepository;
import com.ad_samaria.repositories.LiderazgoRolRepository;
import com.ad_samaria.services.LiderazgoSvc;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
@RequiredArgsConstructor
public class LiderazgoSvcImpl extends CommonSvcImpl<Liderazgo, LiderazgoRepository> implements LiderazgoSvc {

    @Autowired
    private LiderazgoRolRepository liderazgoRolRepository;

    @Autowired
    private LiderazgoMiembroRepository liderazgoMiembroRepository;

    private static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public List<LiderazgoListadoDTO> listar() {
        List<Object[]> rows = repository.listarLiderazgosConTotales();
        List<LiderazgoListadoDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            out.add(new LiderazgoListadoDTO(
                    ((Number) r[0]).longValue(),
                    (String) r[1],
                    r[2] == null ? 0L : ((Number) r[2]).longValue()
            ));
        }
        return out;
    }

    @Override
    public void crear(String nombre) {
        repository.crear(nombre.trim());
    }

    @Override
    public void renombrar(Long id, String nombre) {
        int n = repository.renombrar(id, nombre.trim());
        if (n == 0) {
            throw new RuntimeException("Liderazgo no encontrado");
        }
    }

    @Override
    public void eliminar(Long id) {
        repository.eliminar(id);
    }

    // ----- Roles -----
    @Override
    public List<String> listarRoles(Long liderazgoId) {
        List<Object[]> rows = liderazgoRolRepository.listarPorLiderazgo(liderazgoId);
        List<String> out = new ArrayList<>();
        for (Object[] r : rows) {
            out.add((String) r[2]); // id, liderazgo_id, nombre
        }
        return out;
    }

    @Override
    public void crearRol(Long liderazgoId, String nombre) {
        liderazgoRolRepository.crear(liderazgoId, nombre.trim());
    }

    @Override
    public void eliminarRol(Long rolId) {
        liderazgoRolRepository.eliminar(rolId);
    }

    // ----- Miembros -----
    @Override
    public List<LiderazgoMiembroDTO> listarMiembros(Long liderazgoId) {
        List<Object[]> rows = liderazgoMiembroRepository.listarMiembros(liderazgoId);
        List<LiderazgoMiembroDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            // lm.id, lm.liderazgo_id, lm.persona_id, lm.liderazgo_rol_id, lm.desde, lm.hasta, nombre_persona, nombre_rol
            String desde = r[4] == null ? null : OUT.format(((java.sql.Date) r[4]).toLocalDate());
            String hasta = r[5] == null ? null : OUT.format(((java.sql.Date) r[5]).toLocalDate());
            out.add(new LiderazgoMiembroDTO(
                    ((Number) r[0]).longValue(),
                    ((Number) r[1]).longValue(),
                    ((Number) r[2]).longValue(),
                    r[3] == null ? null : ((Number) r[3]).longValue(),
                    desde, hasta,
                    (String) r[6],
                    (String) r[7]
            ));
        }
        return out;
    }

    @Override
    public void agregarMiembro(Long liderazgoId, Long personaId, Long rolId, String desde) {
        // desde viene yyyy-MM-dd; validación simple
        if (desde == null || desde.trim().isEmpty()) {
            desde = LocalDate.now().toString();
        }
        liderazgoMiembroRepository.agregarMiembro(liderazgoId, personaId, rolId, desde);
    }

    @Override
    public void desactivarMiembro(Long liderazgoMiembroId) {
        int n = liderazgoMiembroRepository.desactivarMiembro(liderazgoMiembroId);
        if (n == 0) {
            throw new RuntimeException("Registro no encontrado o ya inactivo");
        }
    }

}
