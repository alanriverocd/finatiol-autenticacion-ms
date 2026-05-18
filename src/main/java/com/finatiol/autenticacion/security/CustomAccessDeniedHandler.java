package com.finatiol.autenticacion.security;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.web.access.AccessDeniedHandler;

import org.springframework.stereotype.Component;

import com.finatiol.autenticacion.constants.ErrorCodes;

import com.finatiol.autenticacion.constants.ErrorMessages;

import com.finatiol.autenticacion.exception.ApiExceptionResponse;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,

            AccessDeniedException accessDeniedException)

            throws IOException, ServletException {

        ApiExceptionResponse error =
                new ApiExceptionResponse(

                        ErrorCodes.ACCESO_DENEGADO,

                        ErrorMessages.ACCESO_DENEGADO,

                        HttpServletResponse.SC_FORBIDDEN
                );

        response.setStatus(
                HttpServletResponse.SC_FORBIDDEN);

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper()
                .writeValue(
                        response.getOutputStream(),
                        error);
    }
}