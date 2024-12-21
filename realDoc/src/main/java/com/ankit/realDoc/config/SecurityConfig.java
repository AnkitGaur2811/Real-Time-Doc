package com.ankit.realDoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ankit.realDoc.Util.jwtFilter;
import com.ankit.realDoc.service.coustomeuserdetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final jwtFilter jwtFilter;
    private final coustomeuserdetailService customUserDetailsService;

    public SecurityConfig(jwtFilter jwtFilter,coustomeuserdetailService customUserDetailsService) {
        this.jwtFilter = jwtFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)  // Set the custom UserDetailsService
                .passwordEncoder(passwordEncoder())          // Use BCryptPasswordEncoder for password encoding
                .and()
                .build();
    }

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(auth -> auth
                 .requestMatchers("/auth/**").permitAll() // Allow authentication endpoints
                 .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only endpoints
                 .requestMatchers("/manager/**").hasRole("MANAGER") // Manager-only endpoints
                 .requestMatchers("/editor/**").hasRole("EDITOR") // Editor-only endpoints
                 .requestMatchers("/viewer/**").hasRole("VIEWER") // Viewer-only endpoints
                 .anyRequest().authenticated())
                 .formLogin(login -> login.permitAll())
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }
}
