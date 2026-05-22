package com.finatiol.autenticacion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter
            jwtAuthenticationFilter;

    private final CustomAccessDeniedHandler
            customAccessDeniedHandler;

    private final CustomUserDetailsService
            customUserDetailsService;

    private final AuditFilter
            auditFilter;

    public SecurityConfig(

            JwtAuthenticationFilter
                    jwtAuthenticationFilter,

            CustomAccessDeniedHandler
                    customAccessDeniedHandler,

            CustomUserDetailsService
                    customUserDetailsService,

            AuditFilter
                    auditFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;

        this.customAccessDeniedHandler =
                customAccessDeniedHandler;

        this.customUserDetailsService =
                customUserDetailsService;

        this.auditFilter =
                auditFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/auth/**",
                                        "/actuator/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )

                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(
                                customAccessDeniedHandler)
                )

                .authenticationProvider(
                        authenticationProvider())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .addFilterAfter(
                        auditFilter,
                        BasicAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                customUserDetailsService);

        provider.setPasswordEncoder(
                passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
