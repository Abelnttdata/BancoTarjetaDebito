package com.example.BancoAutenticacion.configuracion;

import com.example.BancoAutenticacion.controlador.noLogeado;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NoLogeadoHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({noLogeado.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "No esta autenticado",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
