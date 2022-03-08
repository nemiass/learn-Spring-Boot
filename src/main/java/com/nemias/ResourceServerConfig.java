package com.nemias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer // el resource server es el que almacena las credenciales de usuario
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // llamamos al token service, ademas este tambien es lo que implementamos en SecurityConfig
    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    // Sobreescribieno el metodo, el cual recibe el token servive, previo a eso recibe un "resourceIds"
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Acá se protenge las URL por token
        http
                // .exceptionHandling(), es opcional, se usa para lanzar un Error, el cual se implementará mas adelante
                .exceptionHandling().authenticationEntryPoint(new AuthException())
                 .and()
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs/**" ).authenticated() //.permitAll() para hacerlo accesible a todos
                .antMatchers("/consultas/**" ).authenticated()
                .antMatchers("/especialidades/**" ).authenticated()
                .antMatchers("/examenes/**" ).authenticated()
                .antMatchers("/medicos/**" ).authenticated()
                .antMatchers("/menus/**" ).authenticated()
                //.antMatchers("/tokens/**" ).permitAll()
                .antMatchers("/consultaExamenes/**" ).authenticated()
                .antMatchers("/pacientes/**" ).authenticated();
    }
}
