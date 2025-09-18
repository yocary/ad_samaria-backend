/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.models.Usuarios;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Yocary
 */
public interface UsuariosSvc {

    Usuarios crearUsuario(Usuarios usuario);

    Optional<Usuarios> buscarPorId(Long id);

    List<Usuarios> listar();

    void eliminar(Long id);

    Usuarios actualizar(Long id, Usuarios datos);
}
