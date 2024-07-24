package com.example.hotel.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final MyUserDetailsService userDetailsService;

  public SecurityConfig(MyUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize -> authorize
                    .anyRequest()
                    .permitAll())
        .formLogin(
            form ->
                form.loginPage("/login")
                    .loginProcessingUrl("/loginProcessingUrl")
                    .successHandler(authenticationSuccessHandler())
                    .permitAll())
        .logout(
            logout ->
                logout
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login")
                    .permitAll())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return (request, response, authentication) -> {
      String redirectUrl =
          authentication.getAuthorities().stream()
                  .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
              ? "/admin"
              : "/";
      response.sendRedirect(redirectUrl);
    };
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return userDetailsService;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
}
