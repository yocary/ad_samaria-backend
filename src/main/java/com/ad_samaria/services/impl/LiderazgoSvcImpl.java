/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.CrearRolRequest;
import com.ad_samaria.dto.EditarRolRequest;
import com.ad_samaria.dto.LiderazgoListadoDTO;
import com.ad_samaria.dto.LiderazgoMiembroDTO;
import com.ad_samaria.models.Liderazgo;
import com.ad_samaria.projections.RolListadoProjection;
import com.ad_samaria.repositories.LiderazgoMiembroRepository;
import com.ad_samaria.repositories.LiderazgoRepository;
import com.ad_samaria.repositories.LiderazgoRolRepository;
import com.ad_samaria.repositories.RolSistemaRepository;
import com.ad_samaria.services.LiderazgoSvc;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
@RequiredArgsConstructor
public class LiderazgoSvcImpl extends CommonSvcImpl<Liderazgo, LiderazgoRepository> implements LiderazgoSvc {

    @Autowired
    private LiderazgoRolRepository rolRepository;

    @Autowired
    private LiderazgoMiembroRepository liderazgoMiembroRepository;

    @Autowired
    private RolSistemaRepository rolSistemaRepository;

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
    public List<RolListadoProjection> listarRoles(Long liderazgoId) {
        validarLiderazgo(liderazgoId);
        return rolRepository.listarRoles(liderazgoId);
    }

    @Override
    public void crearRol(Long liderazgoId, CrearRolRequest req) {
        validarLiderazgo(liderazgoId);

        final String nombre = (req.getNombre() == null ? "" : req.getNombre().trim());
        if (nombre.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (rolRepository.contarPorNombre(liderazgoId, nombre) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un rol con ese nombre en este liderazgo");
        }
        rolRepository.crearRol(liderazgoId, nombre);
    }

    @Override
    public void editarRol(Long liderazgoId, Long rolId, EditarRolRequest req) {
        validarLiderazgo(liderazgoId);

        String nombre = req.getNombre();
        if (nombre != null) {
            nombre = nombre.trim();
            if (nombre.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no puede ser vacío");
            }
            if (rolRepository.contarPorNombreExcluyendo(liderazgoId, nombre, rolId) > 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un rol con ese nombre en este liderazgo");
            }
        }

        int updated = rolRepository.editarRol(liderazgoId, rolId, nombre);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado para este liderazgo");
        }
    }

    @Override
    public void eliminarRol(Long liderazgoId, Long rolId) {
        validarLiderazgo(liderazgoId);
        int deleted = rolRepository.eliminarRol(liderazgoId, rolId);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado para este liderazgo");
        }
    }

    private void validarLiderazgo(Long liderazgoId) {
        if (liderazgoId == null || !repository.existsById(liderazgoId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "liderazgoId inválido");
        }
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
    public void agregarMiembro(long liderazgoId, long personaId, long rolId) {
        try {
            int rows = liderazgoMiembroRepository.agregarMiembro(liderazgoId, personaId, rolId);
            if (rows == 0) {
                throw new IllegalStateException("No se insertó el integrante.");
            }
        } catch (DataIntegrityViolationException ex) {
            // Puede ser FK o la UNIQUE (liderazgo_id, persona_id)
            throw new IllegalArgumentException("La persona ya es integrante de este liderazgo o datos inválidos.", ex);
        }
    }

    @Override
    public void desactivarMiembro(Long liderazgoMiembroId) {
        int n = liderazgoMiembroRepository.desactivarMiembro(liderazgoMiembroId);
        if (n == 0) {
            throw new RuntimeException("Registro no encontrado o ya inactivo");
        }
    }

    @Override
    @Transactional
    public void eliminarMiembro(long liderazgoId, long miembroId) {
        int rows = liderazgoMiembroRepository.eliminarMiembro(liderazgoId, miembroId);
        if (rows == 0) {
            // No se encontró la relación o no pertenece a ese liderazgo
            throw new IllegalArgumentException("No existe el integrante especificado en este liderazgo.");
        }
    }

}
