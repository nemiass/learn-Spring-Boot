package com.nemias.repo;

import com.nemias.model.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArchivoRepo extends JpaRepository<Archivo, Integer> {
}
