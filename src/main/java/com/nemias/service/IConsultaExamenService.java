package com.nemias.service;

import com.nemias.model.ConsultaExamen;

import java.util.List;

public interface IConsultaExamenService {

    List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsilta);
}
