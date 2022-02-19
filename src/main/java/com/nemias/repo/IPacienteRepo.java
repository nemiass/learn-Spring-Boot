package com.nemias.repo;

import com.nemias.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repsitory, en este caso no va repository porque el padre de donde hereda este repo ya lo hace
public interface IPacienteRepo extends JpaRepository<Paciente, Integer> {

}
