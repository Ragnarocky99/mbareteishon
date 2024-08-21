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
public class proveedor extends conexion implements sentencias {
    
    private int id;
    private String nombre;
    private String ruc;
    
    public proveedor(){
        this.id = 0;
        this.nombre = "null";
        this.ruc = "null";
    }
    
    public proveedor(int id, String nombre, String ruc) {
        this.id = id;
        this.nombre = nombre;
        this.ruc = ruc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    @Override
    public boolean insertar() {
        
        String sql = "insert into proveedor(ruc, nombre) values(?, ?)";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.ruc);
            stm.setString(2, this.nombre);
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
        
        ArrayList<proveedor> proveedores = new ArrayList<>();
        String sql = "select * from proveedor";
        
        try(

            Connection con = getCon();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);){
            
            while(rs.next()){
                
                int cod = rs.getInt("id_proveedor");
                String ruc = rs.getString("ruc");
                String nom = rs.getString("nombre");
                proveedor p = new proveedor(cod, nom, ruc);
                proveedores.add(p);
                
            }
            
        } 
        catch (SQLException ex){
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return proveedores;
        
    }

    @Override
    public boolean modificar() {
        
        String sql = "Update proveedor set nombre = ?, ruc = ? "
                + "where id_proveedor = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setString(1, this.nombre);
            stm.setString(2, this.ruc);
            stm.setInt(3,this.id);
            
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
        
        String sql = "Delete from proveedor where id_proveedor = ?";
        
        try(Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql))
            
        {
            stm.setInt(1, this.id);
            stm.executeUpdate();
            return true;
            
        } 
        catch (SQLException ex){
            
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
        
    }
    
    public proveedor getProv(int paramId){
        
        proveedor prov = new proveedor();
        
        String sql = "select * from proveedor where id_proveedor = ?";

        try (
                Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

            pst.setInt(1,paramId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    
                    int cod = rs.getInt("id_proveedor");
                    String ruc = rs.getString("ruc");
                    String nom = rs.getString("nombre");
                    prov = new proveedor(cod, nom, ruc);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prov;
        
    }
}
