package com.ovinos.config.security;

import com.ovinos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurator {

    @Autowired
    private UserService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/sheep/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/sheep/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/sheep/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/sheep/**").authenticated()


                        .requestMatchers(HttpMethod.GET, "/batch/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/batch/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/batch/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/batch/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/deads/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/deads/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/deads/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/deads/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/activity/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/activity/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/activity/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/activity/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form

                        .loginProcessingUrl("/login")

                        .successHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        })

                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                        })

                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
