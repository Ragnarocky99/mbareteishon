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
public class detalleFactura extends conexion implements sentencias {
    
    private int nro;
    private int cantidad;
    private int idProducto;
    private int numeroFactura;
    private double montoTotal;
    private double monto;
    private double iva10;
    private String nombre;

    public detalleFactura() {
    }

    public detalleFactura(int nro,int cantidad, int idProducto, int numeroFactura, double montoTotal, double monto, double iva10) {
        this.nro = nro;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.numeroFactura = numeroFactura;
        this.montoTotal = montoTotal;
        this.monto = monto;
        this.iva10 = iva10;
        
    }

    @Override
    public boolean insertar() {

        String sql = "INSERT INTO `detalle_factura` (`cantidad`, `id_producto`, `numero_factura`, `monto_total`, `iva_10`, `monto`) VALUES (?,?,?,?,?,?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.cantidad);
            stm.setInt(2, this.idProducto);
            stm.setInt(3, this.numeroFactura);
            stm.setDouble(4, this.montoTotal);
            stm.setDouble(5, this.iva10);
            stm.setDouble(6, this.monto);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    @Override
    public ArrayList consulta() {

        ArrayList<detalleFactura> detalles = new ArrayList<>();
        detalleFactura d = new detalleFactura();
        String sql = "select * from detalle_factura where numero_factura = ?";

         try (
                Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

            pst.setInt(1, this.idProducto);
            int a = 0;
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    a += 1;
                    int c = rs.getInt("cantidad");
                    int ip = rs.getInt("id_producto");
                    int n = rs.getInt("numero_factura");
                    double mt = rs.getDouble("monto_total");
                    double i10 = rs.getDouble("iva_10");
                    double m = rs.getDouble("monto");
                    
                    d = new detalleFactura(a,c,ip,n,mt,i10,m);
                    detalles.add(d);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return detalles;

    }

    @Override
    public boolean modificar() {

        String sql = "Update detalle_factura set cantidad = ?, id_producto = ?, monto_total = ?, iva_10 = ?, monto = ? "
                + "where numero_factura = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setInt(1, this.cantidad);
            stm.setInt(2, this.idProducto);
            stm.setDouble(3, this.montoTotal);
            stm.setDouble(4, this.iva10);
            stm.setDouble(5, this.monto);
            stm.setInt(6, this.numeroFactura);
            stm.executeUpdate();

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

        String sql = "Delete from detalle_factura where numero_factura = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.numeroFactura);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getIva10() {
        return iva10;
    }

    public void setIva10(double iva10) {
        this.iva10 = iva10;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
