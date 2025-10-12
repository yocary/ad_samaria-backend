/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.AgregarMiembroLiderazgoRequest;
import com.ad_samaria.dto.CrearLiderazgoRequest;
import com.ad_samaria.dto.LiderazgoListadoDTO;
import com.ad_samaria.dto.LiderazgoMiembroDTO;
import com.ad_samaria.models.Liderazgo;
import com.ad_samaria.services.LiderazgoSvc;
import com.ad_samaria.validator.LiderazgoValidator;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/liderazgo")
@RestController
public class LiderazgoController extends CommonController<Liderazgo, LiderazgoSvc, LiderazgoValidator> {

    @GetMapping
    public List<LiderazgoListadoDTO> listar() {
        return service.listar();
    }

    @PostMapping("/crearLiderazgo")
    public void crear(@RequestBody CrearLiderazgoRequest req) {
        service.crear(req.getNombre());
    }

    @PutMapping("/{id}")
    public void renombrar(@PathVariable Long id, @RequestBody CrearLiderazgoRequest req) {
        service.renombrar(id, req.getNombre());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    // -------- Roles --------
    @GetMapping("/{id}/roles")
    public List<String> roles(@PathVariable("id") Long liderazgoId) {
        return service.listarRoles(liderazgoId);
    }

    @PostMapping("/{id}/roles")
    public void crearRol(@PathVariable("id") Long liderazgoId, @RequestBody Map<String, String> body) {
        service.crearRol(liderazgoId, body.getOrDefault("nombre", ""));
    }

    @DeleteMapping("/roles/{rolId}")
    public void eliminarRol(@PathVariable Long rolId) {
        service.eliminarRol(rolId);
    }

    // -------- Miembros --------
    @GetMapping("/{id}/miembros")
    public List<LiderazgoMiembroDTO> miembros(@PathVariable("id") Long liderazgoId) {
        return service.listarMiembros(liderazgoId);
    }

    @PostMapping("/{id}/miembros")
    public void agregarMiembro(@PathVariable("id") Long liderazgoId,
            @RequestBody AgregarMiembroLiderazgoRequest req) {
        service.agregarMiembro(liderazgoId, req.getPersonaId(), req.getRolId(), req.getDesde());
    }

    @DeleteMapping("/miembros/{liderazgoMiembroId}")
    public void desactivar(@PathVariable Long liderazgoMiembroId) {
        service.desactivarMiembro(liderazgoMiembroId);
    }

}
