package com.finatiol.autenticacion.exception;

public class TokenExpiradoException
        extends RuntimeException {

    public TokenExpiradoException(
            String mensaje) {

        super(mensaje);
    }
}