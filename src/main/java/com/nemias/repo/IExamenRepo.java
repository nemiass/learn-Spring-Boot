package com.nemias.repo;

import com.nemias.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamenRepo extends JpaRepository<Examen, Integer> {

}
