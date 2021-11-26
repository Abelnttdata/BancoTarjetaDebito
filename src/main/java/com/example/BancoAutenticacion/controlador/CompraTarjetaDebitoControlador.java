package com.example.BancoAutenticacion.controlador;

import com.example.BancoAutenticacion.Entidad.CompraTarjetaDebito;
import com.example.BancoAutenticacion.servicio.CompraTarjetaDebitoServicio;
import com.example.BancoAutenticacion.servicio.TarjetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compras")
public class CompraTarjetaDebitoControlador {

    @Autowired
    CompraTarjetaDebitoServicio compraTarjetaDebitoServicio;

    @Autowired
    TarjetaServicio tarjetaServicio;



}
