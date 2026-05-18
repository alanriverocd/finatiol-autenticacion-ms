package com.finatiol.autenticacion.exception;

import java.time.LocalDateTime;

public class ApiExceptionResponse {

    private String codigo;

    private String mensaje;

    private int status;

    private LocalDateTime fecha;

    public ApiExceptionResponse(
            String codigo,
            String mensaje,
            int status) {

        this.codigo = codigo;
        this.mensaje = mensaje;
        this.status = status;
        this.fecha = LocalDateTime.now();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}