package com.nemias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenController {

//    @Autowired
//    private ConsumerTokenServices tokenService;
//
//    // ":.*" es para que por la url no tome a los "." como un bloque distinto, sino los tome a todos como variable url
//    @GetMapping(value = "/anular/{tokenId:.*}")
//    public void revokeToken(@PathVariable("tokenId") String token) {
//        // revocando el token, el frontend debe hacer la peticion a esta ruta para revocar el token
//        tokenService.revokeToken(token);
//    }
}
