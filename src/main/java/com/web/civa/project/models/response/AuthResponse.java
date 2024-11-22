package com.web.civa.project.models.response;

import com.web.civa.project.models.dto.UsuarioDto;

public class AuthResponse {
    private UsuarioDto usuario;
    private String accessToken;

    public AuthResponse () {}

    public AuthResponse(UsuarioDto usuario, String accessToken) {
        this.usuario = usuario;
        this.accessToken = accessToken;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
