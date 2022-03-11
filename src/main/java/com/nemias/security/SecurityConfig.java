package com.nemias.security;

import com.nemias.exception.AuthException;
import com.nemias.security.auth.CustomAuthenticationFilter;
import com.nemias.security.auth.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.GET;

@Configuration // indicando que esta clase va ser una configuracion de spring y va ser un Bean
@EnableWebSecurity // habilitando seguridad web
@EnableGlobalMethodSecurity(prePostEnabled = true) // nos servirá para el manejo de roles
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // llamamos al user detail service que es de espring, el cual no nuestro UsuarioService implementa
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // Sobreescribiendo el metodo configure de WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // modificando la rita pro defecto de "localhost:8080/login" de spring security
        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(AuthException.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/menus").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
