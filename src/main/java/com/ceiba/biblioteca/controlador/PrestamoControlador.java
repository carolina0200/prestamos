package com.ceiba.biblioteca.controlador;


import com.ceiba.biblioteca.dominio.SolicitudPrestamo;
import com.ceiba.biblioteca.dominio.servicio.PrestamoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prestamo")
public class PrestamoControlador {

    private final PrestamoServicio servicio;

    public PrestamoControlador(PrestamoServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity prestar(@RequestBody SolicitudPrestamo solicitud) {
        return new ResponseEntity(servicio.prestar(solicitud), HttpStatus.OK);
    }

    @GetMapping(value = "/{id-prestamo}")
    public ResponseEntity obtener(@PathVariable("id-prestamo") int id) {
        return servicio.obtener(id)
                .map(prestamo -> new ResponseEntity<>(prestamo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

