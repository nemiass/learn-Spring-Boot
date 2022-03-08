package com.nemias.service.impl;

import com.nemias.model.ConsultaExamen;
import com.nemias.repo.IConsultaExamenRepo;
import com.nemias.service.IConsultaExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaExamenServiceImpl implements IConsultaExamenService {

    @Autowired
    private IConsultaExamenRepo repo;

    @Override
    public List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsilta) {
        return repo.listarExamenesPorConsulta(idConsilta);
    }


}
