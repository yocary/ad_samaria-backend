/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.CambiarPasswordDTO;
import com.ad_samaria.dto.UsuarioCreateDTO;
import com.ad_samaria.dto.UsuarioItemDTO;
import com.ad_samaria.models.Persona;
import com.ad_samaria.models.Usuario;
import com.ad_samaria.repositories.PersonaRepository;
import com.ad_samaria.repositories.UsuarioRepository;
import com.ad_samaria.services.UsuarioSvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class UsuarioSvcImpl extends CommonSvcImpl<Usuario, UsuarioRepository> implements UsuarioSvc {

    @Autowired
    private PersonaRepository personaRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate jdbc;

    public UsuarioSvcImpl(PersonaRepository personaRepo,
            PasswordEncoder encoder, JdbcTemplate jdbc) {
        this.personaRepo = personaRepo;
        this.encoder = encoder;
        this.jdbc = jdbc;
    }

    @Override
    public Usuario crear(UsuarioCreateDTO req) {
        Persona p = personaRepo.findById(req.getPersonaId())
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        if (repository.existsByPersonaId(p.getId())) {
            throw new EntityExistsException("El miembro ya tiene usuario.");
        }

        repository.findByUsernameIgnoreCase(req.getUsername()).ifPresent(u -> {
            throw new EntityExistsException("Username en uso.");
        });

        Usuario u = new Usuario();
        u.setPersonaId(p.getId());
        u.setUsername(req.getUsername().trim());
        u.setHashPassword(encoder.encode(req.getPassword()));
        u.setActivo(req.getActivo() != null ? req.getActivo() : Boolean.TRUE);
        return repository.save(u);
    }

    @Override
    public List<UsuarioItemDTO> listar() {
        Iterable<Usuario> usuariosIterable = repository.findAll();
        List<Usuario> usuarios = new ArrayList<>();
        usuariosIterable.forEach(usuarios::add);

        return usuarios.stream().map(u -> {
            Optional<Persona> op = personaRepo.findById(u.getPersonaId());
            String nombre = op.map(per -> (per.getNombres() + " " + per.getApellidoPaterno()).trim()).orElse("—");

            String rol = null;
            try {
                rol = jdbc.queryForObject(
                        "SELECT r.nombre FROM ad_samaria.persona_rol_sistema prs "
                        + "JOIN ad_samaria.rol_sistema r ON r.id = prs.id "
                        + "WHERE prs.persona_id = ? LIMIT 1",
                        new Object[]{u.getPersonaId()}, String.class
                );
            } catch (Exception ignore) {
            }

            return new UsuarioItemDTO(u.getId(), u.getPersonaId(), nombre, u.getUsername(), rol, u.getActivo());
        }).collect(Collectors.toList());
    }

    @Override
    public String sugerirUsername(Long personaId) {
        Persona p = personaRepo.findById(personaId)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        // Siempre usar nombre y apellido para sugerir username
        String base = (p.getNombres().split(" ")[0] + "." + p.getApellidoPaterno()).toLowerCase();
        return base.replaceAll("[^a-z0-9.]", "");
    }

    public void cambiarPassword(CambiarPasswordDTO req) {
        Usuario u = repository.findByUsernameIgnoreCase(req.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Validar contraseña actual
        if (!encoder.matches(req.getPasswordActual(), u.getHashPassword())) {
            throw new IllegalArgumentException("Contraseña actual incorrecta");
        }

        // Actualizar contraseña nueva
        u.setHashPassword(encoder.encode(req.getPasswordNuevo()));
        repository.save(u);
    }

}
