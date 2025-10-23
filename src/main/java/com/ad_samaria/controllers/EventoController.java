/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.EventoCreateRequest;
import com.ad_samaria.dto.EventoItemDTO;
import com.ad_samaria.models.Evento;
import com.ad_samaria.services.EventoSvc;
import com.ad_samaria.validator.EventoValidator;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/evento")
@RestController
public class EventoController extends CommonController<Evento, EventoSvc, EventoValidator> {

    // GET /liderazgo/{liderazgoId}/eventos
    @GetMapping("/{liderazgoId}/eventos")
    public List<EventoItemDTO> listar(@PathVariable Long liderazgoId) {
        return service.listarEventos(liderazgoId);
    }

    // POST /liderazgo/{liderazgoId}/eventos
    @PostMapping("/{liderazgoId}/eventos")
    public ResponseEntity<Void> crear(@PathVariable Long liderazgoId,
            @RequestBody EventoCreateRequest req) {
        if (req.getNombre() == null || req.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Date fecha = null;
            if (req.getFecha() != null && !req.getFecha().trim().isEmpty()) {
                // Convertir String -> LocalDate -> Date
                LocalDate localDate = LocalDate.parse(req.getFecha());
                fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            service.crearEvento(
                    liderazgoId,
                    req.getNombre().trim(),
                    fecha,
                    req.getDescripcion()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
