package com.example.BancoAutenticacion.repositorio;

import com.example.BancoAutenticacion.Entidad.CompraTarjetaDebito;
import com.example.BancoAutenticacion.Entidad.Cuenta;
import com.example.BancoAutenticacion.Entidad.TarjetaDebito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TarjetaRepositorio {
    @Autowired
    TarjetaRepositorioDAO tarjetaRepositorioDAO;

    @Autowired
    CompraTarjetaDebitoRepositorioDAO compraTarjetaDebitoRepositorioDAO;


    public String estadoTarjeta(boolean activada){
        if(!activada){
            return "Pendiente por activar";
        }
        return "Tarjeta activada";
    }


    public boolean crearTarjeta(TarjetaDebito tarjetaDebito, Integer idUsuario) {
        try {
            tarjetaDebito.setEstado(estadoTarjeta(tarjetaDebito.isActivada()));
            tarjetaDebito.setIdUsuario(idUsuario);
            tarjetaRepositorioDAO.save(tarjetaDebito);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public List<TarjetaDebito> listarTarjetasDeUsuario(Integer idUsuario) {
        return tarjetaRepositorioDAO.listarTarjetasDeUsuario(idUsuario);
    }


    public Integer encontarTarjeta(Integer idUsuario, Integer tarjetaDebito) {
        return tarjetaRepositorioDAO.encontrarTarjeta(idUsuario, tarjetaDebito);
    }

    public boolean activarTarjeta(Integer idUsuario, TarjetaDebito tarjetaDebito) {
        try {
            TarjetaDebito tarjetaDebito1= tarjetaRepositorioDAO.encontrarTarjetaPorUsuario(idUsuario,tarjetaDebito.getNumTarjeta());
            tarjetaDebito1.setEstado("Tarjeta activada");
            tarjetaDebito1.setActivada(true);
            tarjetaDebito1.setIdCuenta(tarjetaDebito.getIdCuenta());
            tarjetaRepositorioDAO.save(tarjetaDebito1);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Integer buscarNumCuenta(List<Cuenta> cuentaCliente, TarjetaDebito tarjetaDebito) {
        for (int x = 0; x < cuentaCliente.size(); x++){
            if(cuentaCliente.get(x).getIdCuenta() == tarjetaDebito.getIdCuenta()){
                return cuentaCliente.get(x).getIdCuenta();
            }
        }
        return 0;
    }

    public boolean obtenerSaldo(List<Cuenta> cuentaCliente, Integer numCuenta) {
        for (int x = 0; x < cuentaCliente.size(); x++){
            if(cuentaCliente.get(x).getIdCuenta() == numCuenta){
                if(cuentaCliente.get(x).getSaldo() > 10){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean generarNuevoLimiteDeExtraccion(TarjetaDebito tarjetaDebito, Integer idUsuario) {
        try {
            TarjetaDebito tarjetaDebito1 = tarjetaRepositorioDAO.encontrarTarjetaParaNuevoLimite(idUsuario, tarjetaDebito.getNumTarjeta());
            tarjetaDebito1.setLimiteExtraccion(tarjetaDebito.getLimiteExtraccion());
            tarjetaRepositorioDAO.save(tarjetaDebito1);
            return true;
        }catch (Exception e){
           return false;
        }
    }

    public boolean obtenerNumTarjetaParaCompra(Integer numTarjeta) {
           Integer numTar = tarjetaRepositorioDAO.encontrarTarjetaParaCompra(numTarjeta);
           if(numTar == null){
               return false;
           }
           return true;
    }

    public boolean obtenersaldoParaCompra(List<Cuenta> cuentaCliente, CompraTarjetaDebito numTarjeta) {
        for (int x = 0; x < cuentaCliente.size(); x++){
            if(cuentaCliente.get(x).getSaldo() < numTarjeta.getImporte()){
                return true;
            }
        }
        return false;
    }

    public boolean comprar(CompraTarjetaDebito compraTarjetaDebito) {
        try {
            compraTarjetaDebitoRepositorioDAO.save(compraTarjetaDebito);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
