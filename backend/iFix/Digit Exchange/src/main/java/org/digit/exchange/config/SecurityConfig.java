// package org.digit.exchange.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests((authz) -> authz
//                 .requestMatchers("/public/**").permitAll()  // Permit all requests to paths matching /public/**
//                 .anyRequest().authenticated()              // All other requests require authentication
//             )
//             .httpBasic();  // Use HTTP Basic Authentication

//         // ... further configuration as needed

//         return http.build();
//     }
// }
