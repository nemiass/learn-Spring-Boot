package com.nemias.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdConsulta;

    // indicamos la relacion Muchos a 1
    @ManyToOne
    // ademas de ello, indicamos el nombre del campo en donde guardaremos esta relacion, y como tercer parametro
    // le colocamos el apodo de la relacion, esto es opcional, si no se coloca, se autogenera
    @JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_medico"))
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false, foreignKey = @ForeignKey(name = "fk_consulta_especialdiad"))
    private Especialidad especialidad;

    // Para convertir el texto que venga en el json hacien el formato ISO DATE el cual es: "yyyy/mmm/dd hh:mm:ss",
    // ya que eso es lo que usa nuestra Bd
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime fecha;

    // ejemplo de relacion uno a muchos, mappedBy: es el campo por el cual se va mapear la relacion, recordar que en
    // la tabla detalle comsulta está consulta, entonces le indicamos que se va mapear por ese campo que el la tabla
    // detalle consulta se llama "consulta", cascade ya sabemos de que trata cascade, el fetch, LAZY: quiere decir
    // que cuando hagamos la consulta a "consulta" este campo  regresará vacío con el objetivo que lo llenemos despues,
    // oprphaRemoval: si está en verdadero quiere decir que nos va permitir eliminar detalles consultas, aunque tenga
    // una relacion de llave foranea con el padre, y retomando el cascada este funcionarpa cuando se guarde, actualice
    // y elimine
    @OneToMany(mappedBy = "consulta", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch =
        FetchType.LAZY, orphanRemoval = true)
    private List<DetalleConsulta> detalleConsulta;

    public List<DetalleConsulta> getDetalleConsulta() {
        return detalleConsulta;
    }

    public void setDetalleConsulta(List<DetalleConsulta> detalleConsulta) {
        this.detalleConsulta = detalleConsulta;
    }

    public Integer getIdConsulta() {
        return IdConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        IdConsulta = idConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return getIdConsulta().equals(consulta.getIdConsulta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdConsulta());
    }
}
