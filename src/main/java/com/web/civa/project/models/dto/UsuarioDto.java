package com.web.civa.project.models.dto;

public class UsuarioDto {
    private String nombreUsuario;
    private String nombreRol;

    public UsuarioDto() {}

    public UsuarioDto(String nombreUsuario, String nombreRol) {
        this.nombreUsuario = nombreUsuario;
        this.nombreRol = nombreRol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
