package com.finatiol.autenticacion.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService
            jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService =
                jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException,
            IOException {

        String path =
                request.getServletPath();

        System.out.println(
                "PATH: " + path);

        if (path.startsWith("/auth")
                || path.startsWith("/actuator")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        final String authHeader =
                request.getHeader(
                        "Authorization");

        final String jwt;

        final String username;

        System.out.println(
                "AUTH HEADER: " + authHeader);

        if (authHeader == null
                || !authHeader.startsWith(
                        "Bearer ")) {

            System.out.println(
                    "TOKEN INVALIDO O AUSENTE");

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        jwt = authHeader.substring(7);

        System.out.println(
                "JWT TOKEN: " + jwt);

        username =
                jwtService.extractUsername(
                        jwt);

        System.out.println(
                "USERNAME: " + username);

        if (username != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            List<SimpleGrantedAuthority>
                    authorities =

                    jwtService
                            .extractPermisos(jwt)

                            .stream()

                            .map(
                                    SimpleGrantedAuthority::new)

                            .toList();

            System.out.println(
                    "AUTHORITIES: " + authorities);

            UserDetails userDetails =
                    new org.springframework.security.core.userdetails.User(

                            username,

                            "",

                            authorities
                    );

            if (jwtService.isTokenValid(
                    jwt,
                    username)) {

                System.out.println(
                        "TOKEN VALIDO");

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(

                                userDetails,

                                null,

                                userDetails
                                        .getAuthorities()
                        );

                authToken.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authToken);

                System.out.println(
                        "AUTHENTICATED USER");
            }
        }

        filterChain.doFilter(
                request,
                response);
    }
}