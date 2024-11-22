package com.web.civa.project.models.request;

public class RegisterRequest {
    private String nombreUsuario;
    private String clave;
    private Integer idRol;

    public RegisterRequest(){}

    public RegisterRequest(String nombreUsuario, String clave, Integer idRol) {
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.idRol = idRol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}
