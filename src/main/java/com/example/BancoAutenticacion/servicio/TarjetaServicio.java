package com.example.BancoAutenticacion.servicio;

import com.example.BancoAutenticacion.Entidad.CompraTarjetaDebito;
import com.example.BancoAutenticacion.Entidad.Cuenta;
import com.example.BancoAutenticacion.Entidad.TarjetaDebito;
import com.example.BancoAutenticacion.repositorio.TarjetaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TarjetaServicio {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    TarjetaRepositorio tarjetaRepositorio;

    public Integer obtenerId(){
        Integer idUsuario = restTemplate.getForObject("http://localhost:8080/usuarios/enviarid", Integer.class);
        return idUsuario;
    }

        public List<Cuenta> obtenerCuentasClientes(){
        Integer idCliente = obtenerId();
        String url = "http://localhost:8081//cuentas/cuentaByIdUsuario/" + idCliente;
        try {
            ResponseEntity<Cuenta[]> response=
                    restTemplate.getForEntity(
                            url,
                            Cuenta[].class);
            Cuenta[] cuenta = response.getBody();
            List<Cuenta> cuentaList= Arrays.asList(cuenta);
            return cuentaList;
        }catch (Exception e){
            return null;
        }
        }


    public boolean crearTarjetaDebito(TarjetaDebito tarjetaDebito, Integer idUsuario) {
        try {
        tarjetaRepositorio.crearTarjeta(tarjetaDebito,idUsuario);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public List<TarjetaDebito> listarTarjetasDeUsuario(Integer idUsuario) {
        return tarjetaRepositorio.listarTarjetasDeUsuario(idUsuario);
    }

    public Integer econtrarTarjeta(Integer idUsuario, Integer tarjetaDebito) {
        return  tarjetaRepositorio.encontarTarjeta(idUsuario, tarjetaDebito);
    }

    public boolean activarTarjeta(Integer idUsuario, TarjetaDebito tarjetaDebito) {
        return tarjetaRepositorio.activarTarjeta(idUsuario,tarjetaDebito);
    }

    public Integer buscarNumCuenta(List<Cuenta> cuentaCliente, TarjetaDebito tarjetaDebito) {
        return tarjetaRepositorio.buscarNumCuenta(cuentaCliente,tarjetaDebito);
    }

    public boolean obtenersaldo(List<Cuenta> cuentaCliente, Integer numCuenta) {
        return tarjetaRepositorio.obtenerSaldo(cuentaCliente,numCuenta);
    }

    public boolean generarNuevoLimiteDeExtraccion(TarjetaDebito tarjetaDebito, Integer idUsuario) {
        return tarjetaRepositorio.generarNuevoLimiteDeExtraccion(tarjetaDebito,idUsuario);
    }

    public boolean obtenerNumTarjetaParaCompra(Integer numTarjeta) {
        return tarjetaRepositorio.obtenerNumTarjetaParaCompra(numTarjeta);
    }

    public boolean obtenersaldoParaCompra(List<Cuenta> cuentaCliente, CompraTarjetaDebito numTarjeta) {
        return tarjetaRepositorio.obtenersaldoParaCompra(cuentaCliente,numTarjeta);
    }

    public boolean comprar(CompraTarjetaDebito compraTarjetaDebito) {
        return tarjetaRepositorio.comprar(compraTarjetaDebito);
    }
}
