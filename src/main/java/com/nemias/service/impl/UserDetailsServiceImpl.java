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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Este servicio lo extendemos de UserDetailService ya que, este nos da metodos que nos ayudan
// con la gestion de usuarios y roles
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(String.format("Usuario no existe %s", username));
        }

        List<GrantedAuthority> lsRoles = new ArrayList<>();

        usuario.getRoles().forEach(rol -> {
            lsRoles.add(new SimpleGrantedAuthority(rol.getNombre()));
        });
        return User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(lsRoles)
                .disabled(!usuario.isEnabled())
                .build();
        // Usando User el cual nos provee spring security
//        return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(),
//                true,true, true, lsRoles);
    }
}
