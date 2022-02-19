package com.nemias.service.impl;

import com.nemias.model.Paciente;
import com.nemias.repo.IPacienteRepo;
import com.nemias.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteRepo repo;

    @Override
    public void registrar(Paciente pac) {
        repo.save(pac);
    }

    @Override
    public void modificar(Paciente pac) {
        repo.save(pac);
    }

    @Override
    public List<Paciente> listar() {
        return repo.findAll();
    }

    @Override
    public Optional<Paciente> leerPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
