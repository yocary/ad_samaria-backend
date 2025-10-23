/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.AsistenciaItemDTO;
import com.ad_samaria.dto.AsistenciaUpsertDTO;
import com.ad_samaria.models.Asistencia;
import com.ad_samaria.services.AsistenciaSvc;
import com.ad_samaria.validator.AsistenciaValidator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/asistencia")
@RestController
public class AsistenciaController extends CommonController<Asistencia, AsistenciaSvc, AsistenciaValidator> {

    @GetMapping("/liderazgo/{liderazgoId}/eventos/{eventoId}")
    public List<AsistenciaItemDTO> listar(
            @PathVariable Long liderazgoId,
            @PathVariable Long eventoId) {
        return service.listar(liderazgoId, eventoId);
    }

    @PutMapping("/liderazgo/{liderazgoId}/eventos/{eventoId}")
    public ResponseEntity<Void> guardar(
            @PathVariable Long liderazgoId,
            @PathVariable Long eventoId,
            @RequestBody List<AsistenciaUpsertDTO> items) {
        service.guardar(liderazgoId, eventoId, items);
        return ResponseEntity.noContent().build();
    }
}
