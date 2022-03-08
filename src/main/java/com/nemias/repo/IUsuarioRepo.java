package com.nemias.repo;

import com.nemias.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String username);
}
