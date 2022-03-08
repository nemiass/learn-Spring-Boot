package com.nemias;

import com.nemias.model.Usuario;
import com.nemias.repo.IUsuarioRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// importando estáticamente
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AppBackendApplicationTests {

    @Autowired
    private IUsuarioRepo repo;

    @Autowired
    BCryptPasswordEncoder bcrypt;

    @Test
    void crearUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(3);
        usuario.setUsername("jaime");
        // hasheando la contraseña
        usuario.setPassword(bcrypt.encode("000"));
        usuario.setEnabled(true);
        Usuario userSaved = repo.save(usuario);

        assertTrue(userSaved.getPassword().equalsIgnoreCase(usuario.getPassword()));
    }
}
