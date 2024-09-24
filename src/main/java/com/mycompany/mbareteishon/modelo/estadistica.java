/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon.modelo;

import com.mycompany.mbareteishon.clases.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nahue
 */
public class estadistica extends conexion {

    private double cantVendida;
    private int ventR;
    private int stck;
    private String nombre;
    private int cant;

    public double getCantVendida() {
        return cantVendida;
    }

    public void setCantVendida(double cantVendida) {
        this.cantVendida = cantVendida;
    }

    public int getVentR() {
        return ventR;
    }

    public void setVentR(int ventR) {
        this.ventR = ventR;
    }

    public estadistica getStats() {
        estadistica est = new estadistica();

        String sql = "SELECT COUNT(factura.numero_factura) as count, SUM(factura.total_gral) as total FROM factura WHERE factura.activo = 1 AND fecha_emision BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY) - INTERVAL 1 SECOND";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {
                est.setCantVendida(rs.getDouble("total"));
                est.setVentR(rs.getInt("count"));
            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return est;

    }
    
    public ArrayList getProductosVendidos(){
        
        estadistica est = new estadistica();
        ArrayList<estadistica> estats = new ArrayList();
        
        String sql = "SELECT p.Stock ,p.nombre, SUM(df.cantidad) AS cantidad_vendida FROM producto p JOIN detalle_factura df ON p.id_producto = df.id_producto JOIN factura f ON df.numero_factura = f.numero_factura WHERE f.activo = 1 AND f.fecha_emision BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY) - INTERVAL 1 SECOND GROUP BY p.id_producto, p.nombre";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {
                est = new estadistica();
                est.setCant(rs.getInt("cantidad_vendida"));
                est.setNombre(rs.getString("nombre"));
                est.setStck(rs.getInt("Stock"));
                estats.add(est);
            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        for (estadistica estat : estats) {
            System.out.println(estat.getNombre());
        }
        return estats;
    }

    

    public int getStck() {
        return stck;
    }

    public void setStck(int stck) {
        this.stck = stck;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

}
