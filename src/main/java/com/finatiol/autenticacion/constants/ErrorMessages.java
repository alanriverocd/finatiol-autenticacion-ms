package com.finatiol.autenticacion.constants;

public class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String USUARIO_NO_ENCONTRADO =
            "Usuario no encontrado";

    public static final String PASSWORD_INCORRECTO =
            "Password incorrecto";

    public static final String TOKEN_INVALIDO =
            "Token JWT inválido";

    public static final String TOKEN_EXPIRADO =
            "El token JWT ha expirado";

    public static final String TOKEN_MALFORMADO =
            "Token JWT mal formado";

    public static final String TOKEN_NO_ENVIADO =
            "Token JWT no enviado";

    public static final String ACCESO_DENEGADO =
            "Acceso denegado";

    public static final String USUARIO_DESACTIVADO =
            "Usuario desactivado";

    public static final String ERROR_INTERNO =
            "Error interno del servidor";

    public static final String CREDENCIALES_INVALIDAS =
            "Credenciales inválidas";
}