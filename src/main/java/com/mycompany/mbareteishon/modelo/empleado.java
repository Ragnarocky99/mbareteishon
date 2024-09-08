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
import org.springframework.security.crypto.bcrypt.BCrypt;

public class empleado extends conexion implements sentencias {

    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String cargo;
    private String pswd; // Campo para la contraseña

    public empleado() {
    }

    public empleado(int idEmpleado, String nombre, String apellido, String cargo, String pswd) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.pswd = pswd; // Inicializa la contraseña
    }

    // Método para hashear la contraseña con bcrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Método para verificar la contraseña
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.pswd);
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT INTO empleado (nombre, apellido, cargo, pswd) VALUES (?, ?, ?, ?)";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.apellido);
            stm.setString(3, this.cargo);
            stm.setString(4, hashPassword(this.pswd)); // Hashea la contraseña antes de insertarla
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
            String sql = "SELECT * FROM empleado WHERE id_empleado = ?";

            try (Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, paramId);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id_empleado");
                        String nom = rs.getString("nombre");
                        String rol = rs.getString("apellido");
                        String cargo = rs.getString("cargo");
                        String psw = rs.getString("pswd"); // Obtiene la contraseña hasheada
                        e = new empleado(id, nom, rol, cargo, psw);
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(empleado.class.getName()).log(Level.SEVERE, null, ex);
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
                empleado emp = new empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo"),
                        rs.getString("pswd") // Obtiene la contraseña hasheada
                );
                empleados.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE empleado SET nombre = ?, apellido = ?, cargo = ?, pswd = ? WHERE id_empleado = ?";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.apellido);
            stm.setString(3, this.cargo);
            stm.setString(4, hashPassword(this.pswd)); // Hashea la contraseña antes de actualizarla
            stm.setInt(5, this.idEmpleado);
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

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
