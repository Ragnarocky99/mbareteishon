/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.mbareteishon;

/**
 *
 * @author nahue
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String sql = "select * from pedidos where";

        String conId = "";
        String conFecha = "";
        String conEst = "";

        conId = " id_pedidos LIKE ? ";
        conFecha = " fechaEmision BETWEEN ? and ? ";
        conEst = " estado = 1 ";

        sql = sql + conId + conFecha + conEst;
        
        System.out.println(sql);

    }

}
