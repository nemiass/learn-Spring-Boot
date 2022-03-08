package com.nemias.service.impl;

import com.nemias.model.Paciente;
import com.nemias.repo.IPacienteRepo;
import com.nemias.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteRepo repo;

    @Override
    public Paciente registrar(Paciente obj) {
        // retorando el ultimo elemento insertado
        return repo.save(obj);
    }

    @Override
    public Paciente modificar(Paciente obj) {
        return repo.save(obj);
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

    // nuestro metodo de servicio que nos permitirá la paginacion
    @Override
    public Page<Paciente> listarPageable(Pageable pageable) {
        // usamos el findAll de nuestro repo, ya que este es recibe un tipo de dato Pageable, recordar que este
        // pageable debe ser de "org.springframework.data.domain.Pageable"
        return repo.findAll(pageable);
    }
}
