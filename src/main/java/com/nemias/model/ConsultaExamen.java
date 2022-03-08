package com.nemias.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

// Ejemplo de tabla intermedia entre Consulta y Examen, osea modela una relacion de muchos a muchos
@Entity
@IdClass(ConsultaExamenPK.class)
public class ConsultaExamen {

    @Id
    private Examen examen;

    @Id
    private Consulta consulta;

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
