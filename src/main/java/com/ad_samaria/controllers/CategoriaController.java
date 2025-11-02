/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.ActualizarCategoriaReq;
import com.ad_samaria.dto.CategoriaMiniRes;
import com.ad_samaria.dto.CategoriaRes;
import com.ad_samaria.dto.CrearCategoriaReq;
import com.ad_samaria.dto.TipoMovimientoMini;
import com.ad_samaria.models.Categoria;
import com.ad_samaria.services.CategoriaSvc;
import com.ad_samaria.validator.CategoriaValidator;
import java.util.List;
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
@RequestMapping("/categoria")
@RestController
public class CategoriaController extends CommonController<Categoria, CategoriaSvc, CategoriaValidator> {

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaMiniRes>> getCategoriasPorTipo(
            @RequestParam("tipo") String tipo,
            @RequestParam("tesoreriaId") Long tesoreriaId) {
        return ResponseEntity.ok(service.listarPorTesoreriaYTipoNombre(tesoreriaId, tipo));
    }

    @GetMapping("/tipos-movimiento")
    public ResponseEntity<List<TipoMovimientoMini>> getTiposMovimiento() {
        return ResponseEntity.ok(service.listarTipos());
    }

//    @PostMapping("/categorias")
//    public ResponseEntity<CategoriaMiniRes> crearCategoria(@RequestBody CrearCategoriaReq req) {
//        CategoriaMiniRes out = service.crearCategoria(req);
//        return ResponseEntity.ok(out);
//    }
//
//    @PutMapping("/categorias/{id}")
//    public ResponseEntity<CategoriaMiniRes> actualizarCategoria(
//            @PathVariable Long id,
//            @RequestBody ActualizarCategoriaReq req) {
//        return ResponseEntity.ok(service.actualizarCategoria(id, req));
//    }
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaRes> crearCategoria(@RequestBody CrearCategoriaReq req) {
        return ResponseEntity.ok(service.crearCategoria(req)); // ahora types matchean
    }

    // Actualizar categoría (valida pertenencia, no permite mover de tesorería)
    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaRes> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody ActualizarCategoriaReq req) {
        return ResponseEntity.ok(service.actualizarCategoria(id, req));
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        service.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-tesoreria")
    public ResponseEntity<List<CategoriaRes>> listarPorTesoreriaYTipo(
            @RequestParam Long tesoreriaId,
            @RequestParam Long tipoId) {
        return ResponseEntity.ok(service.listarPorTesoreriaYTipo(tesoreriaId, tipoId));
    }

}
