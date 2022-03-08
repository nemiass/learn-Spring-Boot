package com.nemias.service.impl;

import com.nemias.model.Examen;
import com.nemias.repo.IExamenRepo;
import com.nemias.service.IExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenServiceImpl implements IExamenService {

    @Autowired
    private IExamenRepo repo;

    @Override
    public Examen registrar(Examen obj) {
        // retorando el ultimo elemento insertado
        return repo.save(obj);
    }

    @Override
    public Examen modificar(Examen obj) {
        return repo.save(obj);
    }

    @Override
    public List<Examen> listar() {
        return repo.findAll();
    }

    @Override
    public Optional<Examen> leerPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
