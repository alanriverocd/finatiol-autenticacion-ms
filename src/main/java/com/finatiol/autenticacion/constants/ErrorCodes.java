package com.finatiol.autenticacion.constants;

public class ErrorCodes {

    private ErrorCodes() {
    }

    public static final String USUARIO_NO_ENCONTRADO =
            "USR_001";

    public static final String PASSWORD_INCORRECTO =
            "USR_002";

    public static final String TOKEN_INVALIDO =
            "JWT_001";

    public static final String TOKEN_EXPIRADO =
            "JWT_002";

    public static final String TOKEN_MALFORMADO =
            "JWT_003";

    public static final String TOKEN_NO_ENVIADO =
            "JWT_004";

    public static final String ACCESO_DENEGADO =
            "AUTH_001";

    public static final String USUARIO_DESACTIVADO =
            "AUTH_002";

    public static final String ERROR_INTERNO =
            "SYS_001";

    public static final String CREDENCIALES_INVALIDAS =
            "AUTH_003";
}