package com.ceiba.biblioteca.persistencia.crud;

import com.ceiba.biblioteca.dominio.Prestamo;
import org.springframework.data.repository.CrudRepository;

public interface PrestamoCrud extends CrudRepository<Prestamo, Integer> {
    Boolean existsByIdentificacionUsuario(String identificacionUsuario);
}
