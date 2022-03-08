package com.nemias.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
// este debe implementar srialziable, para que esta clase pueda ser serializad y pueda ser llevada a formato BD
public class ConsultaExamenPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_examen", nullable = false)
    private Examen examen;

    @ManyToOne
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaExamenPK that = (ConsultaExamenPK) o;
        return examen.equals(that.examen) && consulta.equals(that.consulta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examen, consulta);
    }
}
