package com.nemias.service;

import com.nemias.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente registrar(Paciente pac);
    void modificar(Paciente pac);
    List<Paciente> listar();
    Optional<Paciente> leerPorId(Integer id);
    void eliminar(Integer id);
}
