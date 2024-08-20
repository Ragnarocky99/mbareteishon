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
public class producto extends conexion implements sentencias{
 
    private int idPro;
    private String desc;
    private int stock;
    private double precio;
    private double costo;

    public producto() {
        this.idPro = -1;
        this.desc = "vacio";
        this.stock = 0;
        this.precio = 0;
        this.costo = 0;
    }
    
    
    
    public producto(int id, String desc, int stock, double precio, double costo) {
        this.idPro = id;
        this.desc = desc;
        this.stock = stock;
        this.precio = precio;
        this.costo = costo;
    }
    
    @Override
    public boolean insertar() {
        
        String sql = "insert into producto (descripcion, Stock, precio, costo)  values(?, ?, ?, ?)";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.desc);
            stm.setInt(2, this.stock);
            stm.setDouble(3, this.precio);
            stm.setDouble(4, this.costo);
            
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }

    @Override
    public ArrayList consulta() {
        
        ArrayList<producto> productos = new ArrayList<>();
        String sql = "select * from producto";
        
        try(

            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                int id = rs.getInt("id_producto");
                String desc = rs.getString("descripcion");
                int stck = rs.getInt("Stock");
                int pr = rs.getInt("precio");
                int co = rs.getInt("costo");
                producto c = new producto(id,desc,stck,pr,co);
                productos.add(c);
                
            }
            
        } 
        catch (SQLException ex){
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return productos;
        
    }

    @Override
    public boolean modificar() {
        
        String sql = "Update producto set descripcion = ?, Stock = ?, precio = ?, costo = ? "
                + "where id_producto = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.desc);
            stm.setInt(2,this.stock);
            stm.setDouble(3,this.precio);
            stm.setDouble(4,this.costo);
            stm.setInt(5, this.idPro);
            
            int rowsUpdated = stm.executeUpdate();
           
            // Verificar si se ha actualizado al menos una fila
            if (rowsUpdated > 0) {
                return true;  // La modificación fue exitosa
            } else {
                return false; // No se realizó ninguna modificación
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }

    @Override
    public boolean eliminar() {
        
        String sql = "Delete from producto where id_producto = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setInt(1, this.idPro);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }
    
    public boolean agregarStock(){
        
        String sql = "Update producto set Stock = Stock + ? "
                + "where id_producto = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            
            stm.setInt(1,this.stock);
            stm.setInt(2, this.idPro);
            
            int rowsUpdated = stm.executeUpdate();
           
            // Verificar si se ha actualizado al menos una fila
            if (rowsUpdated > 0) {
                return true;  // La modificación fue exitosa
            } else {
                return false; // No se realizó ninguna modificación
            }
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
     
        
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

    
    
    
    
}
