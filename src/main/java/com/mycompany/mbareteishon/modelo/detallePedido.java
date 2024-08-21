/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon.modelo;

import com.mycompany.mbareteishon.clases.conexion;
import com.mycompany.mbareteishon.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nahue
 */
public class detallePedido extends conexion implements sentencias {

    private int nro;
    private int idPro;
    private int cantidad;
    private double costo;
    private double costoTotal;
    private int idPed;
    private String desc;

    public detallePedido() {

    }

    public detallePedido(int nro, int idPro, int cantidad, double costo, double costoTotal, int idPed, String desc) {
        this.nro = nro;
        this.idPro = idPro;
        this.cantidad = cantidad;
        this.costo = costo;
        this.costoTotal = costoTotal;
        this.idPed = idPed;
        this.desc = desc;
    }

    

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean insertar() {

        String sql = "insert into detalle_pedido(precio,cantidad,precio_total,id_pedidos,id_producto) values(?, ?, ?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setDouble(1, this.costo);
            stm.setInt(2, this.cantidad);
            stm.setDouble(3, this.costoTotal);
            stm.setInt(4, this.idPed);
            stm.setInt(5, this.idPro);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    @Override
    public boolean modificar() {

        String sql = "Update detalle_pedidos set precio = ? ,cantidad = ?,precio_total = ?,id_producto = ? "
                + "where id_pedidos = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setDouble(1, this.costo);
            stm.setInt(2, this.cantidad);
            stm.setDouble(3, this.costoTotal);
            stm.setInt(4, this.idPed);
            stm.setInt(5, this.idPro);

            int rowsUpdated = stm.executeUpdate();

            // Verificar si se ha actualizado al menos una fila
            if (rowsUpdated > 0) {
                return true;  // La modificación fue exitosa
            } else {
                return false; // No se realizó ninguna modificación
            }

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    @Override
    public boolean eliminar() {

        String sql = "Delete from detalle_pedido where id_pedidos = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idPed);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    @Override
    public ArrayList consulta() {

        int c = 1;
        detallePedido d = new detallePedido();
        ArrayList<detallePedido> detalles = new ArrayList<>();
        String sql = "select * from detalle_pedido where id_pedidos = ?";

        try (
                Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

            pst.setInt(1, this.idPed);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    
                    int idped = rs.getInt("id_pedidos");
                    int idpro = rs.getInt("id_producto");
                    String des = consultaNombre(idpro);
                    int cant = rs.getInt("cantidad");
                    double cost = rs.getDouble("precio");
                    double costT = rs.getDouble("precio_total");
                    int nro = c;
                    c += 1;
                    String desc = d.consultaNombre(idpro);
                    d = new detallePedido(nro, idpro, cant, cost, costT, idped, desc);
                    detalles.add(d);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return detalles;

    }
    
    public String consultaNombre(int id){
        
        String nombre = new String();
        String sql = "select descripcion as nombre from producto where id_producto = ?";

        try (
                Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    
                    nombre = rs.getString("nombre");
                    
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nombre;
        
    }

}
