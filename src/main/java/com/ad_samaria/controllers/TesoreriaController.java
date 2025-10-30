/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.MovimientoRowDTO;
import com.ad_samaria.dto.ResumenDTO;
import com.ad_samaria.dto.TesoreriaRowDTO;
import com.ad_samaria.models.Tesoreria;
import com.ad_samaria.services.TesoreriaSvc;
import com.ad_samaria.validator.TesoreriaValidator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/tesoreria")
@RestController
public class TesoreriaController extends CommonController<Tesoreria, TesoreriaSvc, TesoreriaValidator> {

    public static class CrearTesoreriaReq {

        private String nombre;
        private Boolean estado;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Boolean getEstado() {
            return estado;
        }

        public void setEstado(Boolean estado) {
            this.estado = estado;
        }
    }

    public static class CrearTesoreriaRes {

        private Long id;
        private String mensaje;

        public CrearTesoreriaRes(Long id, String mensaje) {
            this.id = id;
            this.mensaje = mensaje;
        }

        public Long getId() {
            return id;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

    @PostMapping("/crearTesoreria")
    public ResponseEntity<CrearTesoreriaRes> crearTesoreria(@RequestBody CrearTesoreriaReq req) {
        Tesoreria t = service.crearTesoreria(req.getNombre(), req.getEstado());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CrearTesoreriaRes(t.getId(), "Tesorería creada con éxito"));
    }

    @GetMapping("/tesorerias")
    public List<TesoreriaRowDTO> listarTesorerias(
            @RequestParam(defaultValue = "activas") String estado,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "mes") String periodo) {

        return service.listar(estado, q, periodo);
    }

    // GET /api/finanzas/tesorerias/{id}/movimientos?periodo=mes&q=texto
    @GetMapping(value = "/tesorerias/{id}/movimientos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovimientoRowDTO> movimientos(
            @PathVariable("id") Long tesoreriaId,
            @RequestParam(value = "periodo", required = false, defaultValue = "mes") String periodo,
            @RequestParam(value = "q", required = false) String q
    ) {
        return service.listarMovimientos(tesoreriaId, periodo, q);
    }

    // GET /api/finanzas/tesorerias/{id}/resumen?periodo=mes
    @GetMapping(value = "/tesorerias/{id}/resumen", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResumenDTO resumen(
            @PathVariable("id") Long tesoreriaId,
            @RequestParam(value = "periodo", required = false, defaultValue = "mes") String periodo
    ) {
        return service.resumen(tesoreriaId, periodo);
    }
}
