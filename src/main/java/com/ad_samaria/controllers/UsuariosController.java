/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.models.Usuarios;
import com.ad_samaria.services.UsuariosSvc;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    @Autowired
    private UsuariosSvc usuariosSvc;

    @PostMapping
    public ResponseEntity<Usuarios> crear(@RequestBody Usuarios usuario) {
        Usuarios nuevo = usuariosSvc.crearUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> listar() {
        return ResponseEntity.ok(usuariosSvc.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerPorId(@PathVariable Long id) {
        return usuariosSvc.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizar(@PathVariable Long id, @RequestBody Usuarios datos) {
        return ResponseEntity.ok(usuariosSvc.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        usuariosSvc.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
