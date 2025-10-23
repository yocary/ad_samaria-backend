/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.AgregarMiembroRequest;
import com.ad_samaria.dto.CrearLiderazgoRequest;
import com.ad_samaria.dto.CrearRolRequest;
import com.ad_samaria.dto.EditarRolRequest;
import com.ad_samaria.dto.LiderazgoListadoDTO;
import com.ad_samaria.dto.LiderazgoMiembroDTO;
import com.ad_samaria.models.Liderazgo;
import com.ad_samaria.projections.RolListadoProjection;
import com.ad_samaria.services.LiderazgoSvc;
import com.ad_samaria.validator.LiderazgoValidator;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    // LISTAR ROLES
    @GetMapping("/{liderazgoId}/roles")
    public List<RolListadoProjection> listarRoles(@PathVariable Long liderazgoId) {
        return service.listarRoles(liderazgoId);
    }

    @PostMapping("/{liderazgoId}/roles")
    public void crearRol(@PathVariable Long liderazgoId, @Valid @RequestBody CrearRolRequest req) {
        service.crearRol(liderazgoId, req);
    }

    @PutMapping("/{liderazgoId}/roles/{rolId}")
    public void editarRol(@PathVariable Long liderazgoId,
            @PathVariable Long rolId,
            @RequestBody EditarRolRequest req) {
        service.editarRol(liderazgoId, rolId, req);
    }

    @DeleteMapping("/{liderazgoId}/roles/{rolId}")
    public void eliminarRol(@PathVariable Long liderazgoId, @PathVariable Long rolId) {
        service.eliminarRol(liderazgoId, rolId);
    }

    // -------- Miembros --------
    @GetMapping("/{id}/miembros")
    public List<LiderazgoMiembroDTO> miembros(@PathVariable("id") Long liderazgoId) {
        return service.listarMiembros(liderazgoId);
    }

    @PostMapping("/{liderazgoId}/miembros")
    public ResponseEntity<?> agregarMiembro(@PathVariable long liderazgoId,
            @RequestBody AgregarMiembroRequest req) {
        service.agregarMiembro(liderazgoId, req.getPersonaId(), req.getRolId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/miembros/{liderazgoMiembroId}")
    public void desactivar(@PathVariable Long liderazgoMiembroId) {
        service.desactivarMiembro(liderazgoMiembroId);
    }

    @DeleteMapping("/{liderazgoId}/miembros/{miembroLiderazgoId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable long liderazgoId,
            @PathVariable("miembroLiderazgoId") long miembroId) {
        service.eliminarMiembro(liderazgoId, miembroId);
        return ResponseEntity.noContent().build(); // 204
    }

}
