package com.tttn.demowebsite.configurations;

import com.tttn.demowebsite.components.JwtTokenFilter;
import com.tttn.demowebsite.role.Role;
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
                .authorizeHttpRequests(requests ->
                    requests
                            .requestMatchers(
                                    "/swagger-resources/**",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/webjars/**"
                            ).permitAll()
                            .requestMatchers(
                                    ("api/v1/users/register"),
                                    ("api/v1/users/login")
                            )
                            .permitAll()

                            .requestMatchers(GET,
                                    ("api/v1/categories/**")).permitAll()

                            .requestMatchers(POST,
                                    ("api/v1/categories/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(PUT,
                                    ("api/v1/categories/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    ("api/v1/categories/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET,
                                    ("api/v1/brands/**")).permitAll()

                            .requestMatchers(POST,
                                    ("api/v1/brands/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(PUT,
                                    ("api/v1/brands/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    ("api/v1/brands/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(GET,
                                    ("api/v1/products/**")).permitAll()

                            .requestMatchers(POST,
                                    ("api/v1/products/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(PUT,
                                    ("api/v1/products/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    ("api/v1/products/**")).hasAnyRole(Role.ADMIN)

                            .requestMatchers(POST,
                                    ("api/v1/orders")).hasAnyRole(Role.USER,Role.ADMIN)

                            .requestMatchers(GET,
                                    ("api/v1/orders/**")).permitAll()

                            .requestMatchers(PUT,
                                    ("api/v1/orders/**")).hasRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    ("api/v1/orders/**")).hasRole(Role.ADMIN)

                            .requestMatchers(POST,
                                    ("api/v1/order_details")).hasAnyRole(Role.USER,Role.ADMIN)

                            .requestMatchers(GET,
                                    ("api/v1/order_details/**")).permitAll()

                            .requestMatchers(PUT,
                                    ("api/v1/order_details/**")).hasRole(Role.ADMIN)

                            .requestMatchers(DELETE,
                                    ("api/v1/order_details/**")).hasRole(Role.ADMIN)
                            .anyRequest().authenticated()
                );
        return http.build();
    }
}
