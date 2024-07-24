/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon.modelo;

/**
 *
 * @author nahue
 */
public class usuario {
    
    private int idUser;
    private String nombre;
    private String rol;

    public usuario(){
        
        this.idUser = -1;
        this.nombre = "";
        this.rol = "";
        
    }
    
    public usuario(int idUser, String nombre, String rol) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.rol = rol;
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
    
}
