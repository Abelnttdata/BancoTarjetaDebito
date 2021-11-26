package com.example.BancoAutenticacion.Entidad;

import javax.persistence.*;

public class Cuenta {

    private Integer idCuenta;
    private Integer idUsuario;
    private String tipoCuenta;
    private String monedaCuenta;
    private Double saldo;

    public Cuenta(String tipoCuenta, String monedaCuenta, Integer idUsuario, Double saldo) {
        this.tipoCuenta = tipoCuenta;
        this.monedaCuenta = monedaCuenta;
        this.idUsuario = idUsuario;
        this.saldo=saldo;
    }

    public Cuenta() {
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getMonedaCuenta() {
        return monedaCuenta;
    }

    public void setMonedaCuenta(String monedaCuenta) {
        this.monedaCuenta = monedaCuenta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}