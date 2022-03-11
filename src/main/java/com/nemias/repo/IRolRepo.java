package com.nemias.repo;

import com.nemias.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepo extends JpaRepository<Rol, Integer> {

    Rol findRolByNombre(String nombre);
}
