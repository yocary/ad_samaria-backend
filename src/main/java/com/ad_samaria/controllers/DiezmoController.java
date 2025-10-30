/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.dto.CrearDiezmoReq;
import com.ad_samaria.dto.GetDiezmosRes;
import com.ad_samaria.services.DiezmoService;
import java.util.Collections;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RestController
@RequestMapping("/diezmos")
@CrossOrigin(origins = "*")
public class DiezmoController {

    private final DiezmoService service;

    public DiezmoController(DiezmoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<GetDiezmosRes> listar(
            @RequestParam(required = false) String periodo,
            @RequestParam(required = false) String q
    ) {
        return ResponseEntity.ok(service.listar(periodo, q));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearDiezmoReq req) {
        Long id = service.crear(req);
        return ResponseEntity.ok(Collections.singletonMap("id", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody CrearDiezmoReq req) {
        service.actualizar(id, req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
