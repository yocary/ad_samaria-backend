/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.CrearPersonaRequest;
import com.ad_samaria.dto.PersonaMiniDTO;
import com.ad_samaria.models.Persona;
import com.ad_samaria.projections.PersonaMiniProjection;
import com.ad_samaria.repositories.ClasificacionSocialRepository;
import com.ad_samaria.repositories.EstadoCivilRepository;
import com.ad_samaria.repositories.PersonaRepository;
import com.ad_samaria.repositories.SexoRepository;
import com.ad_samaria.repositories.TipoPersonaRepository;
import com.ad_samaria.services.PersonaSvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class PersonaSvcImpl extends CommonSvcImpl<Persona, PersonaRepository> implements PersonaSvc {

    @Autowired
    private SexoRepository sexoRepository;

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @Autowired
    private ClasificacionSocialRepository clasificacionSocialRepository;

    @Autowired
    private TipoPersonaRepository tipoPersonaRepository;

    @Override
    public Persona crearPersona(CrearPersonaRequest req) {
        // Validación simple de DPI duplicado (si envías DPI)
        if (req.dpi != null && !req.dpi.trim().isEmpty()) {
            repository.findByDpi(req.dpi.trim())
                    .ifPresent(p -> {
                        throw new RuntimeException("El DPI ya está registrado");
                    });
        }

        Persona p = new Persona();
        p.setNombres(req.nombres != null ? req.nombres.trim() : null);
        p.setApellidoPaterno(req.apellidoPaterno != null ? req.apellidoPaterno.trim() : null);
        p.setApellidoMaterno(req.apellidoMaterno != null ? req.apellidoMaterno.trim() : null);
        p.setTelefono(req.telefono != null ? req.telefono.trim() : null);
        p.setDpi(req.dpi != null ? req.dpi.trim() : null);
        p.setDireccion(req.direccion != null ? req.direccion.trim() : null);

        if (req.fechaNacimiento != null && !req.fechaNacimiento.trim().isEmpty()) {
            p.setFechaNacimiento(LocalDate.parse(req.fechaNacimiento)); // "yyyy-MM-dd"
        }

        // Set de FK por id
        if (req.sexoId != null) {
            p.setSexoId(req.sexoId.shortValue());
        }
        if (req.estadoCivilId != null) {
            p.setEstadoCivilId(req.estadoCivilId);
        }
        if (req.clasificacionId != null) {
            p.setClasifSocialId(req.clasificacionId);
        }
        if (req.tipoPersonaId != null) {
            p.setTipoPersonaId(req.tipoPersonaId);
        }

        // estatus/ministerio quedan null si no vienen en el formulario
        return repository.save(p);
    }

    @Override
    public List<PersonaMiniDTO> buscarMin(String q) {
        if (q == null) {
            q = "";
        }
        q = q.trim();
        if (q.length() < 2) {
            return Collections.emptyList();
        }

        List<Object[]> rows = repository.buscarMin(q);
        List<PersonaMiniDTO> out = new ArrayList<>();
        for (Object[] r : rows) {
            Long id = r[0] == null ? null : ((Number) r[0]).longValue();
            String nombre = (String) r[1];
            out.add(new PersonaMiniDTO(id, nombre));
        }
        return out;
    }
    
    @Override
    public List<PersonaMiniProjection> listarPersonasMini() {
        return repository.listarPersonasMini();
    }

}
