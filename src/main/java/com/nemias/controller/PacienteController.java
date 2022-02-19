package com.nemias.controller;

import com.nemias.model.Paciente;
import com.nemias.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService service;

    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Optional<Paciente> leerPorId(@PathVariable ("id") Integer id) {
        return service.leerPorId(id);
    }

    @PostMapping
    public void registrar(@RequestBody Paciente pac) {
        service.registrar(pac);
    }

    @PutMapping
    public void modificar(@RequestBody Paciente pac) {
        service.modificar(pac);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable ("id") Integer id) {
        service.eliminar(id);
    }
}
