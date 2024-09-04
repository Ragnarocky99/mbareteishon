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
public class cliente extends conexion implements sentencias {

    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String rucCiCliente;
    private String telefono;
    private String direccion;

    public cliente() {
    }

    public cliente(int idCliente, String nombreCliente, String apellidoCliente, String rucCiCliente, String telefono, String direccion) {

        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.rucCiCliente = rucCiCliente;
        this.telefono = telefono;
        this.direccion = direccion;

    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getRucCiCliente() {
        return rucCiCliente;
    }

    public void setRucCiCliente(String rucCiCliente) {
        this.rucCiCliente = rucCiCliente;
    }

    public cliente getClienteFromDb(int paramId) {
        cliente cl = new cliente();

        if (paramId != 0) {

            String sql = "select * from cliente where id_cliente = ?";

            try (
                    Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

                pst.setInt(1, paramId);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {

                        int cod = rs.getInt("id_cliente");
                        String ruc = rs.getString("ruc_ci");
                        String nom = rs.getString("nombre");
                        String ape = rs.getString("apellido");
                        String tel = rs.getString("telefono");
                        String dir = rs.getString("direccion");
                        cl = new cliente(cod, nom, ape, ruc, tel,dir);

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cl;
    }

    @Override
    public boolean insertar() {

        String sql = "insert into cliente(ruc_ci, nombre, apellido,telefono,direccion) values(?, ?, ?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.rucCiCliente);
            stm.setString(2, this.nombreCliente);
            stm.setString(3, this.apellidoCliente);
            stm.setString(4, this.telefono);
            stm.setString(5, this.direccion);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    @Override
    public ArrayList consulta() {

        ArrayList<cliente> clientes = new ArrayList<>();
        String sql = "select * from cliente";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                int cod = rs.getInt("id_cliente");
                String ruc = rs.getString("ruc_ci");
                String nom = rs.getString("nombre");
                String ape = rs.getString("apellido");
                String tel = rs.getString("telefono");
                String dir = rs.getString("direccion");
                cliente c = new cliente(cod, nom, ape, ruc, tel, dir);
                clientes.add(c);

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return clientes;

    }

    @Override
    public boolean modificar() {

        String sql = "Update cliente set nombre = ?, apellido = ?, ruc_ci = ?, telefono = ?, direccion = ? "
                + "where id_cliente = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombreCliente);
            stm.setString(2, this.apellidoCliente);
            stm.setString(3, this.rucCiCliente);
            stm.setString(4, this.telefono);
            stm.setString(5, this.direccion);
            stm.setInt(6, this.idCliente);
            

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

        String sql = "Delete from cliente where id_cliente = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idCliente);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    

}
