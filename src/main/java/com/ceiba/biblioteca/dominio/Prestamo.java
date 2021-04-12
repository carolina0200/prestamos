package com.ceiba.biblioteca.dominio;

import com.ceiba.biblioteca.dominio.excepcion.ExcepcionMensaje;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PRESTAMOS")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fechaMaximaDevolucion;
    private String isbn;
    private String identificacionUsuario;
    private int tipoUsuario;

    public Prestamo() { }

    public Prestamo(SolicitudPrestamo solicitud, String fechaMaximaDevolucion) {
        this.validaciones(solicitud);
        this.id = 0;
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
        this.isbn = solicitud.getIsbn();
        this.identificacionUsuario = solicitud.getIdentificacionUsuario();
        this.tipoUsuario = solicitud.getTipoUsuario();
    }

    private void validaciones(SolicitudPrestamo solicitud) {
        if(solicitud.getIsbn() == null || solicitud.getIdentificacionUsuario() == null) {
            throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "Ni la identificación, ni el isbn pueden ser nulos");
        }
        if(solicitud.getIsbn().isEmpty() || solicitud.getIdentificacionUsuario().isEmpty()) {
            throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "Ni la identificación, ni el isbn pueden estar vacíos");
        }
        if(solicitud.getIdentificacionUsuario().length() > 10) {
            throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "La identificación " + solicitud.getIdentificacionUsuario() + " no es válida, se requiere una identificación de máximo 10 caracteres");
        }
        if(solicitud.getIsbn().length() > 10) {
            throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "El isbn " + solicitud.getIsbn() + " no es válido, se requiere un isbn de máximo 10 caracteres");
        }
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
