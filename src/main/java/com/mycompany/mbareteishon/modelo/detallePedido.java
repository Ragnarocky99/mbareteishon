/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon.modelo;

/**
 *
 * @author nahue
 */
public class detallePedido {
    
    private int idPro;
    private String desc;
    private int cantidad;
    private double costo;
    private double costoTotal;
    private int idPed;

    public detallePedido(int idPro, String desc, int cantidad, double costo, double costoTotal, int idPed) {
        this.idPro = idPro;
        this.desc = desc;
        this.cantidad = cantidad;
        this.costo = costo;
        this.costoTotal = costoTotal;
        this.idPed = idPed;
    }
    
    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public int getIdPed() {
        return idPed;
    }

    public void setIdPed(int idPed) {
        this.idPed = idPed;
    }
    
    
    
}
