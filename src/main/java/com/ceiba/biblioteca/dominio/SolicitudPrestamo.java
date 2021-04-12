package com.ceiba.biblioteca.dominio;

import com.ceiba.biblioteca.dominio.excepcion.ExcepcionMensaje;
import org.springframework.http.HttpStatus;

public class SolicitudPrestamo {
    private String isbn;
    private String identificacionUsuario;
    private int tipoUsuario;

    public SolicitudPrestamo(String isbn, String identificacionUsuario, int tipoUsuario) {
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.tipoUsuario = tipoUsuario;
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
