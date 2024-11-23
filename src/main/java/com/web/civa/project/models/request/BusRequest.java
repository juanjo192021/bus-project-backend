package com.web.civa.project.models.request;

import java.time.LocalDateTime;

public class BusRequest {

    private String numeroBus;
    private String placa;
    private String caracteristicas;
    private Integer marcaId;
    private String estado;

    public BusRequest() {}

    public BusRequest(String numeroBus, String placa,String caracteristicas, Integer marcaId, String estado) {
        this.numeroBus = numeroBus;
        this.placa = placa;
        this.caracteristicas = caracteristicas;
        this.marcaId = marcaId;
        this.estado = estado;
    }

    public String getNumeroBus() {
        return numeroBus;
    }

    public void setNumeroBus(String numeroBus) {
        this.numeroBus = numeroBus;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
