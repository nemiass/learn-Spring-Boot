package com.nemias.service.impl;

import com.nemias.model.Usuario;
import com.nemias.repo.IUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Este servicio lo extendemos de UserDetailService ya que, este nos da metodos que nos ayudan
// con la gestion de usuarios y roles
@Service
public class UsuarioServiceImpl implements UserDetailsService {

    // Llamamos a el repo de usuarios que creamos anteriormente
    @Autowired
    private IUsuarioRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByUsername(username);
        // validando so existe el usuario
        if (usuario == null) {
            throw new UsernameNotFoundException(String.format("Usuario no existe", username));
        }
        // En spring los roles se les llama GrantedAutority, por lo tanto debemos crear esa lista, el cual viene
        // del usuario consultado en la BD
        List<GrantedAuthority> roles = new ArrayList<>();
        usuario.getRoles().forEach(rol -> roles.add(new SimpleGrantedAuthority(rol.getNombre())));

        // Creamos el UserDetail el cual es el que devuelve este metodo, pero le pasamos como instancia
        // un User, el cual es una Clase que no provee Spring, y a el le pasamos, el username y password y roles
        // de nuestro user de la BD, osea ponblamos con nuesta data al UserDetails
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), roles);
        return userDetails;
    }
}
