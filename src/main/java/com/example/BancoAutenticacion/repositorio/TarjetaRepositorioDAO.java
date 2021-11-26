package com.example.BancoAutenticacion.repositorio;

import com.example.BancoAutenticacion.Entidad.TarjetaDebito;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TarjetaRepositorioDAO extends CrudRepository<TarjetaDebito, Integer> {

    @Query(value = "select t from TarjetaDebito t where t.idUsuario = :idUsuario")
    List<TarjetaDebito> listarTarjetasDeUsuario(Integer idUsuario);

    @Query(value = "select t.numTarjeta from TarjetaDebito t where t.idUsuario = :idUsuario AND t.numTarjeta =:tarjetaDebito AND t.estado= 'Pendiente por activar'")
    Integer encontrarTarjeta(Integer idUsuario, Integer tarjetaDebito);

    @Query(value = "select t from TarjetaDebito t where t.idUsuario = :idUsuario AND t.numTarjeta =:tarjetaDebito AND t.estado= 'Pendiente por activar'")
    TarjetaDebito encontrarTarjetaPorUsuario(Integer idUsuario, Integer tarjetaDebito);

    @Query(value = "select t from TarjetaDebito t where t.idUsuario = :idUsuario AND t.numTarjeta =:numTarjeta")
    TarjetaDebito encontrarTarjetaParaNuevoLimite(Integer idUsuario, Integer numTarjeta);

    @Query(value = "select t.numTarjeta from TarjetaDebito t where t.numTarjeta =:numTarjeta")
    Integer encontrarTarjetaParaCompra(Integer numTarjeta);


}
