package com.nemias.controller;

import com.nemias.model.ConsultaExamen;
import com.nemias.service.IConsultaExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consultaExamanes")
public class ConsultaExamenController {
    
    @Autowired
    private IConsultaExamenService service;

    // Ejempplo de ruta explicita
    @GetMapping(value = "/{idConsulta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConsultaExamen>> listar(@PathVariable("idConsulta") Integer idConsulta) {
        List<ConsultaExamen> consultaExamen = service.listarExamenesPorConsulta(idConsulta);
        return new ResponseEntity<>(consultaExamen, HttpStatus.OK);
    }
}
