package com.finatiol.autenticacion.exception;

public class UsuarioNoEncontradoException
        extends RuntimeException {

    public UsuarioNoEncontradoException(
            String message) {

        super(message);
    }
}
