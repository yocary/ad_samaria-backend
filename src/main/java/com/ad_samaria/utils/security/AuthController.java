package com.ad_samaria.utils.security;

import com.ad_samaria.dto.LoginRequest;
import com.ad_samaria.dto.LoginResponse;
import com.ad_samaria.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ad_samaria.repositories.UsuarioRepository;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JdbcTemplate jdbc;

    public AuthController(UsuarioRepository usuarioRepo, PasswordEncoder encoder,
            JwtUtil jwtUtil, JdbcTemplate jdbc) {
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.jdbc = jdbc;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest req) {
        Usuario u = usuarioRepo.findByUsernameIgnoreCase(req.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!Boolean.TRUE.equals(u.getActivo())) {
            throw new IllegalStateException("Usuario inactivo");
        }
        if (!encoder.matches(req.getPassword(), u.getHashPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // roles desde persona_rol_sistema
        List<String> roles = jdbc.query(
                "SELECT 'ROLE_' || upper(r.nombre) \n" +
"                 FROM ad_samaria.persona_rol_sistema prs \n" +
"                 JOIN ad_samaria.rol_sistema r ON r.id = prs.rol_id  \n" +
"                 WHERE prs.persona_id = ? and prs.activo = true",
                new Object[]{u.getPersonaId()},
                (rs, i) -> rs.getString(1)
        );
        if (roles.isEmpty()) {
            roles = Collections.singletonList("ROLE_USER");
        }

        String token = jwtUtil.generateToken(u.getUsername(), roles);

        return new LoginResponse(token, u.getId(), u.getPersonaId(), u.getUsername(), roles);
    }
}
