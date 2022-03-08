package com.nemias.dto;

// DTO para guardar el procedure de la funcion de la BD
public class ConsultaResumenDTO {

    private String fecha;
    private Integer cantidad;

    public ConsultaResumenDTO() {

    }

    public ConsultaResumenDTO(String fecha, Integer cantidad) {
        super();
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
