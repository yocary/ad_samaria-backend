/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.utils.security;

import com.ad_samaria.models.Usuarios;
import com.ad_samaria.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByUsuario(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UsuarioDetails(usuario);
    }
}
