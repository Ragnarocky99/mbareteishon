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
public class pedido extends conexion implements sentencias {

    private int id;
    private int idprov;
    private String fechaEmision;

    public pedido() {
    }

    public pedido(int id, int idprov, String fechaEmision) {
        this.id = id;
        this.idprov = idprov;
        this.fechaEmision = fechaEmision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdprov() {
        return idprov;
    }

    public void setIdprov(int idprov) {
        this.idprov = idprov;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @Override
    public boolean insertar() {

        String sql = "insert into pedidos(id_pedidos,id_proveedor,fechaEmision) values(?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.id);
            stm.setInt(2, this.idprov);
            stm.setString(3, this.fechaEmision);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }
    
    public int getUltimoPedido(){
        
        int mayor = 0;
        String sql = "select MAX(id_pedidos) as ultimo_pedido from pedidos";
        
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                mayor = rs.getInt("ultimo_pedido");

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return mayor;
        
    }

    @Override
    public ArrayList consulta() {

        ArrayList<pedido> pedidos = new ArrayList<>();
        String sql = "select * from pedidos";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                int idped = rs.getInt("id_pedidos");
                int idprove = rs.getInt("id_proveedor");
                String fecha = rs.getString("fechaEmision");
                pedido p = new pedido(idped, idprove, fecha);
                pedidos.add(p);

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return pedidos;

    }

    @Override
    public boolean modificar() {

        String sql = "Update pedidos set id_proveedor = ? , fechaEmision = ? "
                + "where id_pedidos = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idprov);
            stm.setString(2, this.fechaEmision);
            stm.setInt(3, this.id);

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

        String sql = "Delete from pedidos where id_pedidos = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.id);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

}