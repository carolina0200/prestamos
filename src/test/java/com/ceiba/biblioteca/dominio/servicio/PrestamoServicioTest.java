package com.ceiba.biblioteca.dominio.servicio;
import com.ceiba.biblioteca.dominio.SolicitudPrestamo;
import com.ceiba.biblioteca.dominio.excepcion.ExcepcionMensaje;
import com.ceiba.biblioteca.dominio.respositorio.PrestamoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrestamoServicioTest {

    public static final int USUARIO_AFILIADO = 1;
    public static final int USUARIO_EMPLEADO = 2;
    public static final int USUARIO_INVITADO = 3;
    public static final int USUARIO_INVALIDO = 4;
    private PrestamoRepositorio prestamoRepoMock;
    private PrestamoServicio prestamoServicio;

    @BeforeEach
    void setUp() {
        prestamoRepoMock = mock(PrestamoRepositorio.class);
        prestamoServicio = new PrestamoServicio(prestamoRepoMock);
    }

    @Test
    @DisplayName("Validación de excepción para cuando un usuario invitado intenta sacar un segundo libro, se espera 400")
    void usuarioInvitadoIntentaSacarSegundoLibro() {
        // Arrange
        SolicitudPrestamo solicitud = new SolicitudPrestamo("D435DA","1007238534", USUARIO_INVITADO);
        when(prestamoRepoMock.existe(solicitud.getIdentificacionUsuario())).thenReturn(true);
        try {
            // Act
            prestamoServicio.prestar(solicitud);
            fail();
        } catch (ExcepcionMensaje e) {
            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
            assertEquals("El usuario con identificación 1007238534 ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo", e.getMessage());
        }
    }

    @Test
    @DisplayName("Validación de excepción para un tipo de usuario diferente a los registrados (1,2,3)")
    void tipoDeUsuarioIncorrecto() {
        // Arrange
        SolicitudPrestamo solicitud = new SolicitudPrestamo("D435DA4323","1007238534", USUARIO_INVALIDO);
        try {
            // Act
            prestamoServicio.prestar(solicitud);
            fail();
        } catch (ExcepcionMensaje e) {
            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
            assertEquals("Tipo de usuario no permitido en la biblioteca", e.getMessage());
        }
    }

    @Test
    @DisplayName("Validación de excepción para el Isbn con longitud mayor a 10")
    void longitudDeIsbnMayorALaPermitida() {
        // Arrange
        SolicitudPrestamo solicitud = new SolicitudPrestamo("D435DA43235","1007238534", USUARIO_AFILIADO);
        try {
            // Act
            prestamoServicio.prestar(solicitud);
            fail();
        } catch (ExcepcionMensaje e) {
            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
            assertEquals("El isbn D435DA43235 no es válido, se requiere un isbn de máximo 10 caracteres", e.getMessage());
        }
    }

    @Test
    @DisplayName("Validación de excepción para las identificaciones de usuario con longitud mayor a 10")
    void longitudDeIdentificacionDeUsuarioMayorALaPermitida() {
        // Arrange
        SolicitudPrestamo solicitud = new SolicitudPrestamo("D435DA4323","10072385345", USUARIO_EMPLEADO);
        try {
            // Act
            prestamoServicio.prestar(solicitud);
            fail();
        } catch (ExcepcionMensaje e) {
            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
            assertEquals("La identificación 10072385345 no es válida, se requiere una identificación de máximo 10 caracteres", e.getMessage());
        }
    }

    @Nested
    @DisplayName("Se comprueban las fechas máximas de devolución por cada tipo de usuario")
    class FechaMaximaDevolicionPorUsuarioCorrecta {
        @Test
        void usuarioAfiliadoConFechaMaximaCorrecta() {
            assertEquals(fechaMaximaDeEntrega(10), prestamoServicio.obtenerFechaEntrega(USUARIO_AFILIADO));
        }

        @Test
        void UsuarioEmpleadoConFechaMaximaCorrecta() {
            assertEquals(fechaMaximaDeEntrega(8), prestamoServicio.obtenerFechaEntrega(USUARIO_EMPLEADO));
        }

        @Test
        void usuarioInvitadoConFechaMaximaCorrecta() {
            assertEquals(fechaMaximaDeEntrega(7), prestamoServicio.obtenerFechaEntrega(USUARIO_INVITADO));
        }
    }


    String fechaMaximaDeEntrega(long diasASumar) {
        LocalDate fecha = LocalDate.now();
        int dias = 0;
        while (dias < diasASumar) {
            fecha = fecha.plusDays(1);
            dias = (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) ? dias : ++dias;
        }
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}