package com.nemias.controller;


import com.nemias.dto.UserRoleDTO;
import com.nemias.model.Rol;
import com.nemias.model.Usuario;
import com.nemias.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsers() {
        return ResponseEntity.ok().body(userService.getusuarios());
    }

    @GetMapping("/users/save")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(userService.save(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/role/save")
    public ResponseEntity<Rol> saveUser(@RequestBody Rol rol) {
        return new ResponseEntity<>(userService.saveRol(rol), HttpStatus.CREATED);
    }

    @GetMapping("/role/addTouser")
    public ResponseEntity<?> saveUser(@RequestBody UserRoleDTO userRoleDTO) {
        userService.addRolToUser(userRoleDTO.getUsername(), userRoleDTO.getRolName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
