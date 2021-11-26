package com.example.BancoAutenticacion.configuracion;

import com.example.BancoAutenticacion.controlador.CreacionFallida;
import com.example.BancoAutenticacion.controlador.TodaTarjetaActivada;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TodaTarjetaActivadaHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({TodaTarjetaActivada.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "La tarjeta ya se encuenta activada o no existe",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
