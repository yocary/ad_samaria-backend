/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.models.Usuarios;
import com.ad_samaria.repositories.UsuariosRepository;
import com.ad_samaria.services.UsuariosSvc;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Service
public class UsuariosSvcImpl implements UsuariosSvc {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuarios crearUsuario(Usuarios usuario) {
        // Cifrado de contraseña
        String hash = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContraseña(hash);
        return usuariosRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> buscarPorId(Long id) {
        return usuariosRepository.findById(id);
    }

    @Override
    public List<Usuarios> listar() {
        return usuariosRepository.findAll();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuariosRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Usuarios actualizar(Long id, Usuarios datos) {
        return usuariosRepository.findById(id).map(usuario -> {
            usuario.setNombreCompleto(datos.getNombreCompleto());
            usuario.setCorreo(datos.getCorreo());
            usuario.setNumeroEmpleado(datos.getNumeroEmpleado());
            usuario.setRol(datos.getRol());
            usuario.setEstado(datos.getEstado());
            usuario.setUsuario(datos.getUsuario());

            if (datos.getContrasenia() != null && !datos.getContrasenia().isEmpty()) {
                usuario.setContraseña(passwordEncoder.encode(datos.getContrasenia()));
            }

            return usuariosRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
