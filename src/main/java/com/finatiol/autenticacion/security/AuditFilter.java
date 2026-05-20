package com.finatiol.autenticacion.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.finatiol.autenticacion.service.AuditoriaService;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditFilter
        extends OncePerRequestFilter {

    private final AuditoriaService
            auditoriaService;

    public AuditFilter(
            AuditoriaService auditoriaService) {

        this.auditoriaService =
                auditoriaService;
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain)

            throws ServletException,
            IOException {

        filterChain.doFilter(
                request,
                response);

        Authentication authentication =

                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String usuario =
                "ANONIMO";

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getName() != null) {

            usuario =
                    authentication.getName();
        }

        String endpoint =
                request.getRequestURI();

        String metodo =
                request.getMethod();

        String accion =
                metodo + " " + endpoint;

        if (!endpoint.contains(
                "/swagger-ui")

                && !endpoint.contains(
                        "/v3/api-docs")) {

            auditoriaService.registrar(

                    usuario,

                    metodo,

                    endpoint,

                    accion);
        }
    }
}