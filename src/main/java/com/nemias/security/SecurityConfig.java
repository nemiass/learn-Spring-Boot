package com.nemias.security;

import com.nemias.exception.AuthEntrypointException;
import com.nemias.exception.AuthFailureHandlerException;
import com.nemias.security.auth.JwtAuthenticationFilter;
import com.nemias.security.auth.JwtAuthorizationFilter;
import com.nemias.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.GET;

@Configuration // indicando que esta clase va ser una configuracion de spring y va ser un Bean
@EnableWebSecurity // habilitando seguridad web
@EnableGlobalMethodSecurity(prePostEnabled = true) // nos servirá para el manejo de roles
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // llamamos al user detail service que es de espring, el cual no nuestro UsuarioService implementa
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean JwtAuthorizationFilter customAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // Sobreescribiendo el metodo configure de WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // modificando la rita pro defecto de "localhost:8080/login" de spring security
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(),
                jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new AuthFailureHandlerException());

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(new AuthEntrypointException());
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh").permitAll();
        http.authorizeRequests().antMatchers(GET, "/menus").hasAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(customAuthorizationFilter(), JwtAuthenticationFilter.class);
    }
}
