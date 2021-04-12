package com.ceiba.biblioteca.dominio.servicio;

import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.RespuestaPrestamo;
import com.ceiba.biblioteca.dominio.SolicitudPrestamo;
import com.ceiba.biblioteca.dominio.excepcion.ExcepcionMensaje;
import com.ceiba.biblioteca.dominio.respositorio.PrestamoRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PrestamoServicio {

    public static final int USUARIO_AFILIADO = 1;
    public static final int USUARIO_EMPLEADO = 2;
    public static final int USUARIO_INVITADO = 3;

    private final PrestamoRepositorio repositorio;

    public PrestamoServicio(PrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public RespuestaPrestamo prestar(SolicitudPrestamo solicitud) {
        if(solicitud.getTipoUsuario() < 1 || solicitud.getTipoUsuario() > 3) {
            throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "Tipo de usuario no permitido en la biblioteca");
        }
        if(solicitud.getTipoUsuario() == 3 && repositorio.existe(solicitud.getIdentificacionUsuario())) {
            throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "El usuario con identificación "+ solicitud.getIdentificacionUsuario() +" ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
        }
        Prestamo prestamo = new Prestamo(solicitud, obtenerFechaEntrega(solicitud.getTipoUsuario()));
        return new RespuestaPrestamo(repositorio.prestar(prestamo)) ;
    }

    public Optional<Prestamo> obtener(int id) {
        return repositorio.obtener(id);
    }

    public String obtenerFechaEntrega(int tipoUsuario) {
        switch (tipoUsuario) {
            case USUARIO_AFILIADO:
                return sumarDias(10);
            case USUARIO_EMPLEADO:
                return sumarDias(8);
            case USUARIO_INVITADO:
                return sumarDias(7);
            default:
                throw new ExcepcionMensaje(HttpStatus.BAD_REQUEST, "Tipo de usuario no permitido en la biblioteca");
        }
    }

    String sumarDias(long diasASumar) {
        LocalDate fecha = LocalDate.now();
        int dias = 0;
        while (dias < diasASumar) {
            fecha = fecha.plusDays(1);
            dias = (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) ? dias : ++dias;
        }
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
