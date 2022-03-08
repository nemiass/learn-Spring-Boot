package com.nemias.dto;

import com.nemias.model.Consulta;
import com.nemias.model.Examen;

import java.util.List;

public class ConsultaListaExamenDTO {

    private Consulta consulta;
    private List<Examen> listExamen;

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Examen> getListExamen() {
        return listExamen;
    }

    public void setListExamen(List<Examen> listExamen) {
        this.listExamen = listExamen;
    }
}
