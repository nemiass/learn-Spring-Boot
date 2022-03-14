package com.nemias.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthFailureHandlerException implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException
    {
        String msg = "Credenciales incorrectas";

        if (e instanceof DisabledException) {
            msg = "Estado de la cuenta actualmente desactivada";
        }

        response.setHeader("error", e.getMessage());
        response.setStatus(response.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);

        Map<String, String> error = new HashMap<>();
        error.put("error", "401");
        error.put("message", msg);
        error.put("exception", "No autorizado");
        error.put("path", request.getServletPath());
        error.put("timestamp", LocalDateTime.now().toString());
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
