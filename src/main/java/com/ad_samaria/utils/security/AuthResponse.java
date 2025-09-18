package com.ad_samaria.utils.security;

import java.util.Set;

public class AuthResponse {
    private String jwt;
    private String usuario;
    private Set<String> roles;

    public AuthResponse(String jwt, String usuario, Set<String> roles) {
        this.jwt = jwt;
        this.usuario = usuario;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}