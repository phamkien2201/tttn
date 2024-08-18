package com.tttn.demowebsite.configurations;

import com.tttn.demowebsite.components.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    ("api/v1/users/register"),
                                    ("api/v1/users/login")
                            )
                            .permitAll()
                            .requestMatchers(POST,
                                    ("api/v1/orders")).hasAnyRole("USER","ADMIN")

                            .requestMatchers(GET,
                                    ("api/v1/orders/**")).hasAnyRole("USER","ADMIN")

                            .requestMatchers(PUT,
                                    ("api/v1/orders/**")).hasRole("ADMIN")

                            .requestMatchers(DELETE,
                                    ("api/v1/orders/**")).hasRole("ADMIN")
                            .anyRequest().authenticated();
                });
        return http.build();
    }
}
