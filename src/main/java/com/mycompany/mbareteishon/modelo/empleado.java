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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nahue
 */
public class empleado extends conexion implements sentencias{

    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String cargo;

    public empleado() {
    }

    public empleado(int idEmpleado, String nombre, String apellido, String cargo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT INTO empleado (nombre, apellido, cargo) VALUES (?, ?, ?)";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.apellido);
            stm.setString(3, this.cargo);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public empleado getUserFromDb(int paramId) {
        empleado e = new empleado();

        if (paramId != 0) {

            String sql = "select * from empleado where id_empleado = ?";

            try (
                    Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

                pst.setInt(1, paramId);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {

                        int id = rs.getInt("id_empleado");
                        String nom = rs.getString("nombre");
                        String rol = rs.getString("apellido");
                        String psw = rs.getString("cargo");
                        e = new empleado(id, nom, rol, psw);

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return e;
    }
    
    @Override
    public ArrayList<empleado> consulta() {
        ArrayList<empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                empleado empleado = new empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo")
                );
                empleados.add(empleado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE empleado SET nombre = ?, apellido = ?, cargo = ? WHERE id_empleado = ?";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.apellido);
            stm.setString(3, this.cargo);
            stm.setInt(4, this.idEmpleado);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idEmpleado);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(empleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
