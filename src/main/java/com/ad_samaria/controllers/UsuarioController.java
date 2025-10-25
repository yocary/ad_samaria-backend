/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.CambiarPasswordDTO;
import com.ad_samaria.dto.UsuarioCreateDTO;
import com.ad_samaria.dto.UsuarioItemDTO;
import com.ad_samaria.models.Usuario;
import com.ad_samaria.services.UsuarioSvc;
import com.ad_samaria.validator.UsuarioValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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
@RequestMapping("/usuario")
@RestController
public class UsuarioController extends CommonController<Usuario, UsuarioSvc, UsuarioValidator> {

    @GetMapping
    public List<UsuarioItemDTO> listar() {
        return service.listar();
    }

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioCreateDTO req) {
        try {
            Usuario usuario = service.crear(req);
            return ResponseEntity.ok(usuario);
        } catch (EntityExistsException ex) {
            // 409 CONFLICT si ya existe
            return ResponseEntity.status(409).body(ex.getMessage());
        } catch (Exception ex) {
            // Otros errores
            return ResponseEntity.status(500).body("Error al crear usuario");
        }
    }

    @GetMapping("/sugerir/{personaId}")
    public ResponseEntity<String> sugerir(@PathVariable Long personaId) {
        return ResponseEntity.ok(service.sugerirUsername(personaId));
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<Map<String, String>> cambiarPassword(@RequestBody @Valid CambiarPasswordDTO req) {
        Map<String, String> response = new HashMap<>();
        try {
            service.cambiarPassword(req);
            response.put("message", "Contraseña cambiada exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (EntityNotFoundException e) {
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(404).body(response);
        }
    }

}
