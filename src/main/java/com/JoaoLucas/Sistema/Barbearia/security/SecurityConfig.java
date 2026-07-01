package com.JoaoLucas.Sistema.Barbearia.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/usuarios/v1").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/agendamentos/v1").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/agendamentos/v1/todos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/agendamentos/v1/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/agendamentos/v1/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/servicos/v1/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/servicos/v1/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/v1/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/barbeiro/v1/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/barbeiro/v1/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/clientes/v1/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/v1/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/clientes/v1/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/auth/v1/login").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
