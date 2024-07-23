package com.example.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApplication {

  public static void main(String[] args) {
    SpringApplication.run(HotelApplication.class, args);
  }

  // @Bean
  // CommandLineRunner commandLineRunner(RoleRepository roleRepo) {
  //   return args -> {
  //     roleRepo.saveAll(List.of(new Role("ADMIN"), new Role("USER")));
  //   };
  // }
}
