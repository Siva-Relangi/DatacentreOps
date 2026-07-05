package com.datacentreops.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        //  PUBLIC
                        .requestMatchers("/api/auth/**", "/actuator/health", "/actuator/info", "/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

                        //  IAM
                        .requestMatchers("/api/iam/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/iam/audit-logs/**")
                        .hasAnyRole("COMPLIANCE_OFFICER", "ADMIN")

                        //  CAPACITY
                        .requestMatchers("/api/capacity/**")
                        .hasAnyRole("DCOPS_ENGINEER", "ADMIN")

                        //  CUSTOMER
                        .requestMatchers("/api/colocation/**")
                        .hasAnyRole("SALES_MANAGER", "ADMIN")

                        //  ENVIRONMENTAL
                        .requestMatchers("/api/environmental/**")
                        .hasAnyRole("FACILITIES_ENGINEER", "DCOPS_ENGINEER", "ADMIN")

                        //  CONNECTIVITY
                        .requestMatchers("/api/connectivity/carrier-presence/**")
                        .hasAnyRole("DCOPS_ENGINEER", "ADMIN")

                        .requestMatchers("/api/connectivity/cross-connects/**")
                        .hasAnyRole("COLO_CUSTOMER", "DCOPS_ENGINEER", "ADMIN")

                        //  INFRASTRUCTURE CONFIG
                        .requestMatchers(HttpMethod.POST, "/api/infrastructure/data-halls/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/infrastructure/data-halls/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/infrastructure/data-halls/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/infrastructure/data-halls/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/infrastructure/racks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/infrastructure/racks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/infrastructure/racks/**").hasRole("ADMIN")

                        .requestMatchers("/api/infrastructure/assets/**")
                        .hasAnyRole("COLO_CUSTOMER", "DCOPS_ENGINEER", "ADMIN")

                        //  WORK ORDERS
                        .requestMatchers("/api/work-orders/*/assign")
                        .hasAnyRole("DCOPS_ENGINEER", "ADMIN")

                        .requestMatchers("/api/work-orders/*/status")
                        .hasAnyRole("DCOPS_ENGINEER", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/work-orders/**")
                        .hasAnyRole("DCOPS_ENGINEER", "ADMIN")

                        .requestMatchers("/api/work-orders/**")
                        .hasAnyRole("COLO_CUSTOMER", "DCOPS_ENGINEER", "ADMIN")

                        .requestMatchers("/api/work-order-notes/**")
                        .hasAnyRole("COLO_CUSTOMER", "DCOPS_ENGINEER", "ADMIN")

                        //  NOTIFICATIONS
                        .requestMatchers("/api/notifications/**").authenticated()

                        //  DEFAULT
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, ex1) -> {
                            if (Boolean.TRUE.equals(req.getAttribute(JwtAuthenticationFilter.AUTHENTICATED_ATTR))) {
                                res.sendError(403, "Forbidden");
                            } else {
                                res.sendError(401, "Unauthorized");
                            }
                        })
                        .accessDeniedHandler((req, res, ex2) -> res.sendError(403, "Forbidden"))
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}