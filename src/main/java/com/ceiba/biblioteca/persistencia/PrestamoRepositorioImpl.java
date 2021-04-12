package com.ceiba.biblioteca.persistencia;

import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.respositorio.PrestamoRepositorio;
import com.ceiba.biblioteca.persistencia.crud.PrestamoCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PrestamoRepositorioImpl implements PrestamoRepositorio {

    private final PrestamoCrud crud;

    @Autowired
    public PrestamoRepositorioImpl(PrestamoCrud prestamoCrud) {
        this.crud = prestamoCrud;
    }

    @Override
    public Prestamo prestar(Prestamo prestamo) {
        return crud.save(prestamo);
    }

    @Override
    public Optional<Prestamo> obtener(int id) {
        return crud.findById(id);
    }

    @Override
    public Boolean existe(String identificacionUsuario) {
        return crud.existsByIdentificacionUsuario(identificacionUsuario);
    }
}
