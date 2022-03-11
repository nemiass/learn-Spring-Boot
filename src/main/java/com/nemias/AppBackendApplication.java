package com.nemias;

import com.nemias.model.Rol;
import com.nemias.service.IUserService;
import com.nemias.service.impl.UserDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppBackendApplication.class, args);
    }
//    @Bean
//    CommandLineRunner run(IUserService userService) {
//        return args -> {
//            userService.saveRol(new Rol("ROLE_ADMIN"));
//            userService.saveRol(new Rol("ROLE_USER"));
//        }
//    }
}
