/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.PersonaRolDTO;
import com.ad_samaria.models.PersonaRolSistema;
import com.ad_samaria.services.PersonaRolSistemaSvc;
import com.ad_samaria.validator.PersonaRolSistemaValidator;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RestController
@RequestMapping("/persona-rol")
public class PersonaRolSistemaController extends CommonController<PersonaRolSistema, PersonaRolSistemaSvc, PersonaRolSistemaValidator> {

    public static class AsignarReq {

        public Long personaId;
        public Short rolId;
    }

    @PostMapping("/asignar")
    public PersonaRolSistema asignar(@RequestBody AsignarReq req) {
        return service.asignar(req.personaId, req.rolId);
    }

    @DeleteMapping("/{id}")
    public void quitar(@PathVariable Long id) {
        service.quitar(id);
    }

    @GetMapping("/persona/{personaId}")
    public List<PersonaRolDTO> listarPorPersona(@PathVariable Long personaId) {
        return service.listarPorPersona(personaId);
    }
}
