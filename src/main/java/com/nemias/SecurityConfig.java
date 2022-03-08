package com.nemias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration // indicando que esta clase va ser una configuracion de spring y va ser un Bean
@EnableWebSecurity // habilitando seguridad web
@EnableGlobalMethodSecurity(prePostEnabled = true) // nos servirá para el manejo de roles
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Accediendo a los valores de nuestro "application.properties", el cual lo usaremos
    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.encoding-strength}")
    private Integer encodingStrength;

    @Value("${security.security-realm}")
    private String securityRealm;

    // Llamamos a BCryptPasswordEnccoder, el viene integrado von spring
    @Autowired
    private BCryptPasswordEncoder bcrypt;

    // llamamos al user detail service que es de espring, el cual no nuestro UsuarioService implementa
    @Autowired
    private UserDetailsService userDetailsService;


    // Añadiendo in Bean, este nos devolverá una instancia de Bcrypt, el objetivo es hacer un autowired
    // de esto, y este nos devuelva la instancia, ademas esta en un @Configuration, y esto lo carga Spring
    // por lo tando se puede llamar a esto
    @Bean
    //@Scope(scopeName = "SINGLETON") -> ejemplo de ambito singleton, lo podeos usar así o dejarlo ambito por defecto
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    // Hacemos un Bean de un AutenticationManager, el cual es un gestor de como obtener la informacion de
    // nuestro usuario, además este va ser usuado en el metodo de la line 69, osea obtendremos la instancia
    // en tiempo de ejecucion que nos dá este metodo.
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Este metodo es un Autowired porque hace uso de AutenticationManager ya que AuthenticationManagerBuilder es
    // parte de AuthenticationManager, osea busca una instancia de AuthenticationManagerBuilder y esta isntancia
    // lo hemos creando en el Bean de la linea 57, (Esto es posible porque el Bean y Autowired es posible a nivel
    // de metodos y constrctores)
    // - En la instancia auth le pasamos nuestro UserDetailService el cual sabemos tiene nuestos datos de nuestro
    // usuario, tambien le pasamos un password encoder que el bcrypt que tamvien declaramos anteriormente, el cual se
    // va encargar de codificar para ver si las contraseña hace march con la BD
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
    }

    // Sobreescribiendo el metodo configure de WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                // la gestion de Sesion se le pone sin estado, porque en backend rest no hay estado de coo guardar
                // la sesion
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                // Colocando Nombre a la prticion
                .realmName(securityRealm)
                .and()
                // Desabilitamos el CCRF, ya que es una api REST, y vamos a generar un token
                .csrf()
                .disable();
    }

    // Instancias para poder Crear el Token
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        // Instanciamos el token
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // Le pasamos la firma y retornamos la instancia
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        // es para probar tokenss nivel de memoria, y el otro a nivel de base de datos, e incluso se le psas nuestro
        // AccessTokenConverter
        return new JwtTokenStore(accessTokenConverter());
        //return new JdbcTokenStore(this.dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        // Instanciamos nuestro servicio de tokens
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        // le pasamos nuestro tokn store
        defaultTokenServices.setTokenStore(tokenStore());
        // habilitamos en token de refresco, el cual se activa cuando el token anterior se vence
        defaultTokenServices.setSupportRefreshToken(true);
        // Cuando ya se refresca el token, queremos que continue refrescandose, en este caso está falso, ya que
        // queremos que el usuario se fuerze a hacer login nuevamente para generar el token
        defaultTokenServices.setReuseRefreshToken(false);
        // retornamos el token service
        return defaultTokenServices;
    }
}
