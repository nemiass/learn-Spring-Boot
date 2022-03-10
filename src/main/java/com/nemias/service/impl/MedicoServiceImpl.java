package com.nemias.service.impl;

import com.nemias.model.Medico;
import com.nemias.repo.IMedicoRepo;
import com.nemias.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements IMedicoService {

    @Autowired
    private IMedicoRepo repo;

    @Override
    public Medico registrar(Medico obj) {
        // retorando el ultimo elemento insertado
        return repo.save(obj);
    }

    @Override
    public Medico modificar(Medico obj) {
        return repo.save(obj);
    }

    // Protegiendo el bloque de listado
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PreAuthorize("@restAuthServiceImpl.hasAcces('listar')")
    @Override
    public List<Medico> listar() {
        return repo.findAll();
    }

    @Override
    public Optional<Medico> leerPorId(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
