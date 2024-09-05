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
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author nahue
 */
public class usuario extends conexion implements sentencias {

    private int idUser;
    private String nombre;
    private String rol;
    private String pswd;

    public usuario() {

        this.idUser = -1;
        this.nombre = "";
        this.rol = "";
        this.pswd = "";

    }

    public usuario(int idUser, String nombre, String rol, String pswd) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.rol = rol;
        this.pswd = pswd;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    // Método para hashear la contraseña antes de almacenarla
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean insertar() {
        // Hashear la contraseña antes de almacenarla
        String hashedPassword = hashPassword(this.pswd);

        String sql = "insert into usuarios(nombre, rol, pswd) values(?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.rol);
            stm.setString(3, hashedPassword); // Usar la contraseña hasheada
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Otros métodos de la clase...
    // Método para comparar contraseñas ingresadas con las almacenadas
    public boolean verificarPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    public ArrayList<usuario> consulta() {
        ArrayList<usuario> usuarios = new ArrayList<>();
        String sql = "select * from usuarios";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_user");
                String nom = rs.getString("nombre");
                String rol = rs.getString("rol");
                String psw = rs.getString("pswd");
                usuario u = new usuario(id, nom, rol, psw);
                usuarios.add(u);
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuarios;
    }

    public usuario getUserFromDb(int paramId) {
        usuario u = new usuario();

        if (paramId != 0) {

            String sql = "select * from usuarios where id_user = ?";

            try (
                    Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

                pst.setInt(1, paramId);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {

                        int id = rs.getInt("id_user");
                        String nom = rs.getString("nombre");
                        String rol = rs.getString("rol");
                        String psw = rs.getString("pswd");
                        u = new usuario(id, nom, rol, psw);

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return u;
    }

    @Override
    public boolean modificar() {
        // Hashear la nueva contraseña antes de almacenarla
        String hashedPassword = hashPassword(this.pswd);

        String sql = "Update usuarios set nombre = ?, rol = ?, pswd = ? where id_user = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.nombre);
            stm.setString(2, this.rol);
            stm.setString(3, hashedPassword); // Usar la contraseña hasheada
            stm.setInt(4, this.idUser);

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {

        String sql = "Delete from usuarios where id_user = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idUser);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

}
