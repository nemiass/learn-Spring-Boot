package com.nemias.controller;

import com.nemias.dto.TokensDto;
import com.nemias.dto.UserRoleDTO;
import com.nemias.model.Rol;
import com.nemias.model.Usuario;
import com.nemias.service.IRefreshTokenService;
import com.nemias.service.IUserService;
import com.nemias.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRefreshTokenService refreshTokenService;

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

    @PostMapping("/token/refresh")
    public ResponseEntity<TokensDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        TokensDto tokenDto = refreshTokenService.refreshToken(authorizationHeader);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
}
