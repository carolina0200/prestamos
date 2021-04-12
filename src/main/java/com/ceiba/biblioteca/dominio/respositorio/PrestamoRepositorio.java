package com.ceiba.biblioteca.dominio.respositorio;

import com.ceiba.biblioteca.dominio.Prestamo;

import java.util.Optional;

public interface PrestamoRepositorio {
    Prestamo prestar(Prestamo prestamo);
    Optional<Prestamo> obtener(int id);
    Boolean existe(String identificacionUsuario);
}
