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
public class producto extends conexion implements sentencias {

    private int idPro;
    private String nombre;
    private String desc;
    private int stock;
    private double precio;
    private double costo;

    public producto() {

    }

    public producto(int id, String nombre, String desc, int stock, double precio, double costo) {
        this.idPro = id;
        this.nombre = nombre;
        this.desc = desc;
        this.stock = stock;
        this.precio = precio;
        this.costo = costo;
    }

    @Override
    public boolean insertar() {

        String sql = "insert into producto (nombre ,descripcion, Stock, precio, costo)  values(?, ?, ?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.desc);
            stm.setInt(3, this.stock);
            stm.setDouble(4, this.precio);
            stm.setDouble(5, this.costo);

            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    @Override
    public ArrayList consulta() {

        ArrayList<producto> productos = new ArrayList<>();
        String sql = "select * from producto";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                int id = rs.getInt("id_producto");
                String nom = rs.getString("nombre");
                String desc = rs.getString("descripcion");
                int stck = rs.getInt("Stock");
                int pr = rs.getInt("precio");
                int co = rs.getInt("costo");
                producto c = new producto(id, nom, desc, stck, pr, co);
                productos.add(c);

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return productos;

    }

    @Override
    public boolean modificar() {

        String sql = "Update producto set nombre = ?, descripcion = ?, Stock = ?, precio = ?, costo = ? "
                + "where id_producto = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.desc);
            stm.setInt(3, this.stock);
            stm.setDouble(4, this.precio);
            stm.setDouble(5, this.costo);
            stm.setInt(6, this.idPro);

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

        String sql = "Delete from producto where id_producto = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idPro);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    public boolean agregarStock(int a) {
        this.stock += a;

        String sql = "Update producto set Stock = ? where id_producto = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setInt(1, this.stock);
            stm.setInt(2, this.idPro);

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

    public boolean eliminarStock(int a) {
        this.stock -= a;

        String sql = "Update producto set Stock = ? where id_producto = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setInt(1, this.stock);
            stm.setInt(2, this.idPro);

            int rowsUpdated = stm.executeUpdate();

            // Verificar si se ha actualizado al menos una fila
            if (rowsUpdated > 0) {
                System.out.println("Se elimino " + a + "De Stock para " + this.idPro);
                System.out.println("Quedando " + this.stock);
                return true;  // La modificación fue exitosa
            } else {
                return false; // No se realizó ninguna modificación
            }

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }
    
    public int getStockById(int id){
        int stck = -1;
        
        String sql = "select Stock as stck from producto where id_producto = ?";

        try (
                Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    
                    stck = rs.getInt("stck");
                    
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stck;
    }

    public int getId() {
        return idPro;
    }

    public void setId(int id) {
        this.idPro = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int Stock) {
        this.stock = Stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
