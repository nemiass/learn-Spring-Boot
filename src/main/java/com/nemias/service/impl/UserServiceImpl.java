package com.nemias.service.impl;

import com.nemias.model.Rol;
import com.nemias.model.Usuario;
import com.nemias.repo.IRolRepo;
import com.nemias.repo.IUsuarioRepo;
import com.nemias.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUsuarioRepo repo;

    @Autowired
    private IRolRepo rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setEnabled(true);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }

    @Override
    public Rol saveRol(Rol rol) {
        return rolRepo.save(rol);
    }

    @Override
    @Transactional
    public void addRolToUser(String username, String rolName) {
        Usuario user = repo.findByUsername(username);
        Rol rol = rolRepo.findRolByNombre(rolName);
        user.getRoles().add(rol);
    }

    @Override
    public Usuario getUsuario(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public List<Usuario> getusuarios() {
        return repo.findAll();
    }
}
