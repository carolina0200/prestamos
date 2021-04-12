package com.ceiba.biblioteca.dominio;

public class RespuestaPrestamo {
    private int id;
    private String fechaMaximaDevolucion;

    public RespuestaPrestamo(Prestamo prestamo)  {
        this.id = prestamo.getId();
        this.fechaMaximaDevolucion = prestamo.getFechaMaximaDevolucion();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaMaximaDevolucion() {
        return fechaMaximaDevolucion;
    }

    public void setFechaMaximaDevolucion(String fechaMaximaDevolucion) {
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }
}
