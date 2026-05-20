package com.finatiol.autenticacion.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.finatiol.autenticacion.constants.ErrorCodes;

import com.finatiol.autenticacion.constants.ErrorMessages;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            UsuarioNoEncontradoException.class)
    public ResponseEntity<ApiExceptionResponse>
    handleUsuarioNoEncontradoException(
            UsuarioNoEncontradoException ex) {

        ApiExceptionResponse response =
                new ApiExceptionResponse(

                        ErrorCodes.USUARIO_NO_ENCONTRADO,

                        ex.getMessage(),

                        HttpStatus.NOT_FOUND.value()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            PasswordIncorrectoException.class)
    public ResponseEntity<ApiExceptionResponse>
    handlePasswordIncorrectoException(
            PasswordIncorrectoException ex) {

        ApiExceptionResponse response =
                new ApiExceptionResponse(

                        ErrorCodes.PASSWORD_INCORRECTO,

                        ex.getMessage(),

                        HttpStatus.UNAUTHORIZED.value()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(
            TokenExpiradoException.class)
    public ResponseEntity<ApiExceptionResponse>
    handleTokenExpiradoException(
            TokenExpiradoException ex) {

        ApiExceptionResponse response =
                new ApiExceptionResponse(

                        ErrorCodes.TOKEN_EXPIRADO,

                        ex.getMessage(),

                        HttpStatus.UNAUTHORIZED.value()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(
            TokenInvalidoException.class)
    public ResponseEntity<ApiExceptionResponse>
    handleTokenInvalidoException(
            TokenInvalidoException ex) {

        ApiExceptionResponse response =
                new ApiExceptionResponse(

                        ErrorCodes.TOKEN_INVALIDO,

                        ex.getMessage(),

                        HttpStatus.UNAUTHORIZED.value()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(
            AccessDeniedException.class)
    public ResponseEntity<ApiExceptionResponse>
    handleAccessDeniedException(
            AccessDeniedException ex) {

        ApiExceptionResponse response =
                new ApiExceptionResponse(

                        ErrorCodes.ACCESO_DENEGADO,

                        ErrorMessages.ACCESO_DENEGADO,

                        HttpStatus.FORBIDDEN.value()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse>
    handleException(
            Exception ex) {

        ex.printStackTrace();

        ApiExceptionResponse response =
                new ApiExceptionResponse(

                        ErrorCodes.ERROR_INTERNO,

                        ex.getMessage(),

                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}