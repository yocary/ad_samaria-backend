/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.CrearPersonaRequest;
import com.ad_samaria.dto.PersonaMiniDTO;
import com.ad_samaria.models.Persona;
import com.ad_samaria.projections.PersonaMiniProjection;
import com.ad_samaria.repositories.ClasificacionSocialRepository;
import com.ad_samaria.repositories.EstadoCivilRepository;
import com.ad_samaria.repositories.SexoRepository;
import com.ad_samaria.repositories.TipoPersonaRepository;
import com.ad_samaria.services.PersonaSvc;
import com.ad_samaria.validator.PersonaValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/persona")
@RestController
public class PersonaController extends CommonController<Persona, PersonaSvc, PersonaValidator> {

    @Autowired
    private SexoRepository sexoRepository;

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    @Autowired
    private ClasificacionSocialRepository clasificacionSocialRepository;

    @Autowired
    private TipoPersonaRepository tipoPersonaRepository;

    @PostMapping("/form")
    public Persona crear(@RequestBody CrearPersonaRequest req) {
        return service.crearPersona(req);
    }

    // --- catálogos para llenar selects ---
    @GetMapping("/sexos")
    public List<?> sexos() {
        return sexoRepository.findAllByOrderByNombreAsc();
    }

    @GetMapping("/estados-civiles")
    public List<?> estadosCiviles() {
        return estadoCivilRepository.findAllByOrderByNombreAsc();
    }

    @GetMapping("/clasificaciones")
    public List<?> clasificaciones() {
        return clasificacionSocialRepository.findAllByOrderByNombreAsc();
    }

    @GetMapping("/tipos-persona")
    public List<?> tiposPersona() {
        return tipoPersonaRepository.findAllByOrderByNombreAsc();
    }

    @GetMapping("/buscar")
    public List<PersonaMiniDTO> buscar(@RequestParam("q") String q) {
        return service.buscarMin(q);
    }

    @GetMapping("/listar-todos")
    public List<PersonaMiniProjection> listarTodosMini() {
        return service.listarPersonasMini();
    }
}
