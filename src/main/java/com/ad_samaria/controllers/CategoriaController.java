/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.CategoriaMiniRes;
import com.ad_samaria.dto.CrearCategoriaReq;
import com.ad_samaria.dto.TipoMovimientoMini;
import com.ad_samaria.models.Categoria;
import com.ad_samaria.services.CategoriaSvc;
import com.ad_samaria.validator.CategoriaValidator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/categoria")
@RestController
public class CategoriaController extends CommonController<Categoria, CategoriaSvc, CategoriaValidator> {

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaMiniRes>> getCategoriasPorTipo(@RequestParam("tipo") String tipo) {
        return ResponseEntity.ok(service.listarPorTipoCategoria(tipo));
    }

    // ▼ NUEVO: obtener lista para el <select> de tipos
    @GetMapping("/tipos-movimiento")
    public ResponseEntity<List<TipoMovimientoMini>> getTiposMovimiento() {
        return ResponseEntity.ok(service.listarTipos());
    }

    // ▼ NUEVO: crear categoría
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaMiniRes> crearCategoria(@RequestBody CrearCategoriaReq req) {
        CategoriaMiniRes out = service.crearCategoria(req);
        return ResponseEntity.ok(out);
    }
}
