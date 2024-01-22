package com.example.demo.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        var userDetailsService = new InMemoryUserDetailsManager();
        UserDetails user1 = User.builder()
                .username("postman")
                .password(this.passwordEncoder().encode("admin"))
                .authorities("read")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(this.passwordEncoder().encode("admin"))
                .authorities("read","write")
                .build();

        UserDetails alumne = User.builder()
                .username("alumne")
                .password(this.passwordEncoder().encode("alumne"))
                .authorities("read","write")
                .build();
        userDetailsService.createUser(user1);
        userDetailsService.createUser(admin);
        userDetailsService.createUser(alumne);
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
