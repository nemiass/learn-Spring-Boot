package com.nemias.service.impl;

import com.nemias.model.Rol;
import com.nemias.model.Usuario;
import com.nemias.repo.IRolRepo;
import com.nemias.repo.IUsuarioRepo;
import com.nemias.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// Este servicio lo extendemos de UserDetailService ya que, este nos da metodos que nos ayudan
// con la gestion de usuarios y roles
@Service
public class UserDetailsServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUsuarioRepo repo;

    @Autowired
    private IRolRepo rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(String.format("Usuario no existe %s", username));
        }

        List<GrantedAuthority> lsRoles = new ArrayList<>();

        usuario.getRoles().forEach(rol -> {
            lsRoles.add(new SimpleGrantedAuthority(rol.getNombre()));
        });
        // Usando User el cual nos provee spring security
        return new User(usuario.getUsername(), usuario.getPassword(), lsRoles);
    }

    @Override
    public Usuario save(Usuario usuario) {
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
