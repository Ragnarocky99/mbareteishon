package com.mycompany.mbareteishon.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conexion {
    
    private String bdNombre;
    private String host;
    private String usuario;
    private String pswd;
    Connection con;

    public conexion(String bdNombre, String host, String usuario, String pswd) {
        this.bdNombre = bdNombre;
        this.host = host;
        this.usuario = usuario;
        this.pswd = pswd;
    }

    public conexion() {
        this.bdNombre = "heladeriabd";
        this.host = "localhost";
        this.usuario = "root";
        this.pswd = "";
    }

    public Connection getCon(){
        try {
            String url = ("jdbc:mysql://"+host+":3306/"+bdNombre);
            con = DriverManager.getConnection(url,usuario,pswd);
            System.out.println("Conectado");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    
    public String getBdNombre() {
        return bdNombre;
    }

    public void setBdNombre(String bdNombre) {
        this.bdNombre = bdNombre;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
    
}

