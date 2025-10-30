/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.CrearMovimientoReq;
import com.ad_samaria.models.Movimiento;
import com.ad_samaria.projections.CategoriaMini;
import com.ad_samaria.services.CategoriaSvc;
import com.ad_samaria.services.MovimientoSvc;
import com.ad_samaria.validator.MovimientoValidator;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/movimiento")
@RestController
public class MovimientoController extends CommonController<Movimiento, MovimientoSvc, MovimientoValidator> {

    @Autowired
    private CategoriaSvc categoriaSvc;

    @PostMapping(value = "/tesorerias/{id}/movimientos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearMovimiento(
            @PathVariable("id") Long tesoreriaId,
            @RequestBody CrearMovimientoReq req
    ) {
        Long id = service.crearDesdeDTO(tesoreriaId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("id", id));
    }

    @GetMapping(value = "/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoriaMini> categoriasPorTipo(
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "tipoId", required = false) Long tipoId
    ) {
        return categoriaSvc.listarPorTipo(tipo, tipoId);
    }

    @DeleteMapping("/tesorerias/{id}/movimientos/{movId}")
    public ResponseEntity<Void> eliminarMovimiento(
            @PathVariable("id") Long tesoreriaId,
            @PathVariable("movId") Long movimientoId
    ) {
        service.eliminar(tesoreriaId, movimientoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/tesorerias/{id}/movimientos/{movId}")
    public ResponseEntity<?> actualizarMovimiento(
            @PathVariable("id") Long tesoreriaId,
            @PathVariable("movId") Long movimientoId,
            @RequestBody CrearMovimientoReq req // puedes usar el mismo DTO
    ) {
        service.actualizarDesdeDTO(tesoreriaId, movimientoId, req);
        return ResponseEntity.noContent().build();
    }

}
