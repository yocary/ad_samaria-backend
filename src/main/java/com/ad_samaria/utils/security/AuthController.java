package com.ad_samaria.utils.security;

import com.ad_samaria.dto.LoginRequest;
import com.ad_samaria.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ad_samaria.models.Usuarios;
import com.ad_samaria.services.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationService.autenticar(
            loginRequest.getUsuario(),
            loginRequest.getContrasenia()
        );

        UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();
        Usuarios u = usuarioDetails.getUsuarioEntity();
        String token = jwtUtil.generateToken(u.getUsuario());

        return ResponseEntity.ok(new LoginResponse(token, u.getNombreCompleto(), u.getRol()));
    }
}
