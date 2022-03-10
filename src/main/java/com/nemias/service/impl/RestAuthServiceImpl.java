package com.nemias.service.impl;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RestAuthServiceImpl {

    public boolean hasAcces(String path) {
        boolean rpta = false;

        // aplicando logica para validar
        String metodoRol = "";
        // se evalua la ruta que llega de acuerdo al path, y de acuerdo a ese path se tiene su rol en la variable
        // metodoRol
        switch (path) {
            case "listar":
                metodoRol = "ADMIN";
                break;
            case "listarPorId":
                metodoRol = "ADMIN,USER,DBA";
                break;
        }

        String []metodoRoles = metodoRol.split(",");

        // Verficando si el rol que está entrando del token es un admin, user o data.
        // con la linea 28 nos ayuda a a traer informacion del ususario lofueado en ese momento
        Authentication autho = SecurityContextHolder.getContext().getAuthentication();
            // validando que el ususario que se autentica no sea un usuario anonimo
        if (!(autho instanceof AnonymousAuthenticationToken)) {
            System.out.println(autho.getName());
            // recorremos los roles, osea los GrandtedAutorithies que tenemos
            for (GrantedAuthority auth : autho.getAuthorities()) {
                String rolUser = auth.getAuthority(); // obtenemos el rol
                System.out.println(rolUser);

                // verificando si el rol del ususario obtenido "rolUser" está dentro de los roles que este
                // tiene permitido de acuerdo al path, si tiene el rol retorna true
                for (String rolMet : metodoRoles) {
                    if (rolUser.equalsIgnoreCase(rolMet)) {
                        rpta = true;
                    }
                }
            }
        }
        return rpta;
    }
}
