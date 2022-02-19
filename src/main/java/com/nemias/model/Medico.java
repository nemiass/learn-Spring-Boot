package com.nemias.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedico;

    @Size(min = 3, message = "Nombres debe tener minimo 3 caracteres")
    @Column(name = "nombres", nullable = false, length = 70)
    private String nombres;

    @Size(min = 3, message = "Apellidos debe tener minimo 3 caracteres")
    @Column(name = "apellidos", nullable = false, length = 70)
    private String apellidos;

    @Column(name = "CMP", nullable = false, length = 12)
    private String CMP;

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCMP() {
        return CMP;
    }

    public void setCMP(String CMP) {
        this.CMP = CMP;
    }
}
