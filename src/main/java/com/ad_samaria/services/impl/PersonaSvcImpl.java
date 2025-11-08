/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.CrearPersonaRequest;
import com.ad_samaria.dto.FichaFamiliaDTO;
import com.ad_samaria.dto.FichaGrupoDTO;
import com.ad_samaria.dto.FichaLiderazgoDTO;
import com.ad_samaria.dto.PersonaFichaDTO;
import com.ad_samaria.dto.PersonaMiniDTO;
import com.ad_samaria.models.Persona;
import com.ad_samaria.projections.PersonaFichaCabeceraProjection;
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
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


        Persona p = new Persona();
        p.setNombres(req.nombres != null ? req.nombres.trim() : null);
        p.setApellidoPaterno(req.apellidoPaterno != null ? req.apellidoPaterno.trim() : null);
        p.setApellidoMaterno(req.apellidoMaterno != null ? req.apellidoMaterno.trim() : null);
        p.setTelefono(req.telefono != null ? req.telefono.trim() : null);
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

    @Override
    @Transactional(readOnly = true)
    public PersonaFichaDTO obtenerFicha(Long personaId) {
        PersonaFichaCabeceraProjection cab = repository.obtenerCabecera(personaId)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada: " + personaId));

        List<FichaFamiliaDTO> familias = repository.familiasDePersona(personaId).stream()
                .map(r -> new FichaFamiliaDTO(r.getFamiliaId(), r.getFamiliaNombre(), r.getRolFamiliar()))
                .collect(Collectors.toList());

        List<FichaGrupoDTO> grupos = repository.gruposDePersona(personaId).stream()
                .map(r -> new FichaGrupoDTO(r.getGrupoId(), r.getGrupoNombre(), r.getMinisterio()))
                .collect(Collectors.toList());

        List<FichaLiderazgoDTO> liderazgos = repository.liderazgosDePersona(personaId).stream()
                .map(r -> new FichaLiderazgoDTO(
                r.getLiderazgoId(),
                r.getLiderazgo(),
                r.getRol(),
                r.getDesde(),
                r.getHasta()
        ))
                .collect(Collectors.toList());

        return new PersonaFichaDTO(
                cab.getId(),
                cab.getNombreCompleto(),
                cab.getEstatus(),
                cab.getClasificacion(),
                cab.getTipoPersona(),
                cab.getEstadoCivil(),
                cab.getSexo(),
                cab.getEdad(),
                cab.getFechaNacimiento(),
                cab.getTelefono(),
                cab.getDireccion(),
                cab.getMinisterio(),
                familias, grupos, liderazgos
        );
    }

}
