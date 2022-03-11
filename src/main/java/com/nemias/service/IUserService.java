package com.nemias.service;

import com.nemias.model.Rol;
import com.nemias.model.Usuario;

import java.util.List;

public interface IUserService {

    Usuario save(Usuario usuario);
    Rol saveRol(Rol rol);
    void addRolToUser(String username, String rolName);
    Usuario getUsuario(String username);
    List<Usuario> getusuarios();
}
