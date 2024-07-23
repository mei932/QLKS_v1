package com.example.hotel.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitDatabase {


    @Bean
    CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println(passwordEncoder.encode("admin"));
            
        };
    }
}
