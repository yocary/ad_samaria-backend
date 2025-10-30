/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.utils.security;

import com.ad_samaria.models.Usuario;
import com.ad_samaria.repositories.UsuarioRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;
    private final JdbcTemplate jdbc;

    public CustomUserDetailsService(UsuarioRepository repo, JdbcTemplate jdbc) {
        this.repo = repo;
        this.jdbc = jdbc;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!Boolean.TRUE.equals(u.getActivo())) {
            throw new UsernameNotFoundException("Usuario inactivo");
        }

        List<String> roles = jdbc.query(
                "SELECT DISTINCT 'ROLE_' || UPPER(r.nombre) "
                + "FROM ad_samaria.persona_rol_sistema prs "
                + "JOIN ad_samaria.rol_sistema r ON r.id = prs.rol_id "
                + // ← corrección aquí
                "WHERE prs.persona_id = ? AND prs.activo = true",
                new Object[]{u.getPersonaId()},
                (rs, i) -> rs.getString(1)
        );

        if (roles.isEmpty()) {
            roles = Collections.singletonList("ROLE_USER");
        }

        Collection<? extends GrantedAuthority> auths
                = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new User(u.getUsername(), u.getHashPassword(), auths);
    }
}
