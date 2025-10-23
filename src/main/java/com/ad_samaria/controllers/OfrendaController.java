/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.Ofrenda;
import com.ad_samaria.services.OfrendaSvc;
import com.ad_samaria.validator.OfrendaValidator;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/ofrenda")
@RestController
public class OfrendaController extends CommonController<Ofrenda, OfrendaSvc, OfrendaValidator> {

    @GetMapping("/{eventoId}/ofrendas")
    public List<Ofrenda> listar(@PathVariable Long eventoId) {
        return service.listarPorEvento(eventoId);
    }

    public static class OfrendaCreateRequest {

        @NotNull
        public String fecha;          // 'yyyy-MM-dd'
        @NotNull
        public BigDecimal cantidad;   // >= 0
        public String descripcion;
    }

    @PostMapping("/{eventoId}/ofrendas")
    public ResponseEntity<Void> crear(@PathVariable Long eventoId,
            @RequestBody OfrendaCreateRequest req) {
        try {
            Date f = new SimpleDateFormat("yyyy-MM-dd").parse(req.fecha);
            service.crearParaEvento(eventoId, f, req.cantidad, req.descripcion);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ParseException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody OfrendaCreateRequest req) {
        try {
            // Buscar la ofrenda existente
            Ofrenda ofrendaExistente = service.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Ofrenda no encontrada"));

            // Actualizar campos
            Date f = new SimpleDateFormat("yyyy-MM-dd").parse(req.fecha);
            ofrendaExistente.setFecha(f);
            ofrendaExistente.setDescripcion(req.descripcion);
            ofrendaExistente.setCantidad(req.cantidad);

            service.save(ofrendaExistente);
            return ResponseEntity.ok().build();
        } catch (ParseException ex) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ofrenda> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
