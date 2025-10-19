/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.CrearFamiliaRequest;
import com.ad_samaria.dto.RolDto;
import com.ad_samaria.models.Familia;
import com.ad_samaria.models.FamiliaPersona;
import com.ad_samaria.projections.FamiliaMiembroProjection;
import com.ad_samaria.projections.FamiliaMiniProjection;
import com.ad_samaria.services.FamiliaPersonaSvc;
import com.ad_samaria.services.FamiliaSvc;
import com.ad_samaria.services.RolFamiliarSvc;
import com.ad_samaria.validator.FamiliaValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/familia")
@RestController
public class FamiliaController extends CommonController<Familia, FamiliaSvc, FamiliaValidator> {

    @Autowired
    private RolFamiliarSvc rolSvc;

    @Autowired
    private FamiliaPersonaSvc famPerSvc;

    // === Familias ===
    @GetMapping("/listar")
    public List<FamiliaMiniProjection> listar(@RequestParam(value = "q", required = false) String q) {
        return service.listar(q);
    }

    @PostMapping("/crearFamilia")
    public Familia crear(@RequestBody CrearFamiliaRequest req) {
        return service.crear(req.getName());
    }

    @PutMapping("/actualizarNombre/{id}")
    public ResponseEntity<Void> actualizarNombre(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nombre = body.get("name");
        service.actualizarNombre(id, nombre);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    // === Miembros de familia ===
    @GetMapping("/{familiaId}/miembros")
    public List<FamiliaMiembroProjection> listarMiembros(@PathVariable Long familiaId) {
        return service.listarMiembros(familiaId);
    }

//    @PostMapping("/{familiaId}/miembros")
//    public FamiliaPersona agregarMiembro(@PathVariable Long familiaId, @RequestBody AgregarMiembroRequest req) {
//        return service.agregarMiembro(familiaId, req.getPersonaId(), req.getRolFamId());
//    }
    @DeleteMapping("/miembros/{familiaPersonaId}")
    public void eliminarMiembro(@PathVariable Long familiaPersonaId) {
        service.eliminarMiembro(familiaPersonaId);
    }

    // GET /familia/roles
    @GetMapping("/roles")
    public List<RolDto> roles() {
        return rolSvc.listar();
    }

    // POST /familia/{familiaId}/miembros   body: { personaId, rolFamId }
    @PostMapping("/{familiaId}/miembros")
    public FamiliaPersona agregarMiembro(@PathVariable Long familiaId, @RequestBody Map<String, Object> b) {
        Long personaId = ((Number) b.get("personaId")).longValue();
        Short rolFamId = ((Number) b.get("rolFamId")).shortValue();
        return famPerSvc.agregar(familiaId, personaId, rolFamId);
    }
//
//    // DELETE /familia/miembros/{familiaPersonaId}
//    @DeleteMapping("/miembros/{familiaPersonaId}")
//    public ResponseEntity<Void> eliminarVinculo(@PathVariable Long familiaPersonaId) {
//        service.eliminar(familiaPersonaId);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Long id) {
        var fam = service.obtenerPorId(id);
        if (fam == null) {
            return ResponseEntity.notFound().build();
        }
        // convertir a {id, name} para Angular
        Map<String, Object> map = new HashMap<>();
        map.put("id", fam.getId());
        map.put("name", fam.getNombre());
        return ResponseEntity.ok(map);
    }

}
