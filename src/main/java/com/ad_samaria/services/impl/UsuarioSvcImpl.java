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
import java.text.Normalizer;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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

        // Obtener primera letra del nombre
        String primeraLetraNombre = p.getNombres().trim().substring(0, 1).toLowerCase();

        // Obtener apellido paterno completo
        String apellidoPaterno = p.getApellidoPaterno().toLowerCase();

        // Obtener primera letra del apellido materno (si existe)
        String primeraLetraMaterno = "";
        if (p.getApellidoMaterno() != null && !p.getApellidoMaterno().trim().isEmpty()) {
            primeraLetraMaterno = p.getApellidoMaterno().trim().substring(0, 1).toLowerCase();
        }

        // Combinar todo sin puntos
        String usernameBase = primeraLetraNombre + apellidoPaterno + primeraLetraMaterno;

        // Quitar tildes y caracteres especiales
        String usernameLimpio = quitarTildesYCaracteresEspeciales(usernameBase);

        // Verificar si ya existe y agregar número secuencial si es necesario
        return generarUsernameUnico(usernameLimpio, personaId);
    }

    private String quitarTildesYCaracteresEspeciales(String texto) {
        if (texto == null) {
            return "";
        }

        // Normalizar y quitar tildes
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD);
        String sinTildes = normalized.replaceAll("[\\u0300-\\u036f]", "");

        // Quitar cualquier caracter que no sea letra o número
        return sinTildes.replaceAll("[^a-z0-9]", "");
    }

    private String generarUsernameUnico(String usernameBase, Long personaIdActual) {
        // Primero verificar si el username base ya existe
        Optional<Usuario> usuarioExistente = repository.findByUsernameIgnoreCase(usernameBase);

        // Si no existe, usar el base (usando isPresent() en lugar de isEmpty())
        if (!usuarioExistente.isPresent()) {
            return usernameBase;
        }

        // Si existe, verificar si es para la misma persona (caso de edición)
        // Esto depende de cómo esté mapeada la relación en tu clase Usuario
        Usuario usuario = usuarioExistente.get();

        // Si tu clase Usuario tiene una referencia directa a Persona, sería:
        // if (usuario.getPersona() != null && usuario.getPersona().getId().equals(personaIdActual))
        // Si no tiene referencia directa, verifica por el método existsByPersonaId
        if (repository.existsByPersonaId(personaIdActual)) {
            // Si esta persona ya tiene un usuario, mantener el username actual
            return usernameBase;
        }

        // Si existe para otra persona, buscar variantes con números
        int contador = 1;
        String usernamePropuesto;

        do {
            usernamePropuesto = usernameBase + contador;
            Optional<Usuario> usuarioConNumero = repository.findByUsernameIgnoreCase(usernamePropuesto);

            // Si no existe este username con número
            if (!usuarioConNumero.isPresent()) {
                break;
            }

            contador++;
        } while (contador < 1000); // Límite por seguridad

        return usernamePropuesto;
    }

    @Transactional
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
