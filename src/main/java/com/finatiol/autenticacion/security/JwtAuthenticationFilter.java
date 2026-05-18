package com.finatiol.autenticacion.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.User;

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

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authHeader.substring(7);

        String username =
                jwtService.extractUsername(token);

        if (username != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

        	List<String> permisos =
        	        jwtService.extractPermisos(
        	                token);

        	List<SimpleGrantedAuthority> authorities =
        	        permisos.stream()

        	                .map(SimpleGrantedAuthority::new)

        	                .toList();

        	User user =
        	        new User(
        	                username,
        	                "",
        	                authorities);

        	UsernamePasswordAuthenticationToken authToken =
        	        new UsernamePasswordAuthenticationToken(
        	                user,
        	                null,
        	                authorities);

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request));

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}