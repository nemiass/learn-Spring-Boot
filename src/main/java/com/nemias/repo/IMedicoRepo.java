package com.nemias.repo;

import com.nemias.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repsitory, en este caso no va repository porque el padre de donde hereda este repo ya lo hace
public interface IMedicoRepo extends JpaRepository<Medico, Integer> {

}
