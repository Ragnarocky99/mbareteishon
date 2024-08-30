/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon.modelo;

import com.mycompany.mbareteishon.VistaBuscarPedidosController;
import com.mycompany.mbareteishon.clases.conexion;
import com.mycompany.mbareteishon.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
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
    private int estado;
    private double montoTotal;

    public pedido() {
    }

    public pedido(int id, int idprov, String fechaEmision, int estado, double montoTotal) {
        this.id = id;
        this.idprov = idprov;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.montoTotal = montoTotal;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public boolean insertar() {

        String sql = "insert into pedidos(id_pedidos,id_proveedor,fechaEmision, estado, montoTotal) values(?, ?, ?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.id);
            stm.setInt(2, this.idprov);
            stm.setString(3, this.fechaEmision);
            stm.setInt(4, this.estado);
            stm.setDouble(5, this.montoTotal);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    public int getUltimoPedido() {

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

    public int getPrimerPedido() {

        int menor = 0;
        String sql = "select MIN(id_pedidos) as primer_pedido from pedidos";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                menor = rs.getInt("primer_pedido");

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return menor;

    }

    public pedido getPedido(int paramId) {

        pedido ped = new pedido();

        if (paramId != 0) {

            String sql = "select * from pedidos where id_pedidos = ?";

            try (
                    Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

                pst.setInt(1, paramId);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {

                        int idped = rs.getInt("id_pedidos");
                        int idprove = rs.getInt("id_proveedor");
                        String fecha = rs.getString("fechaEmision");
                        int est = rs.getInt("estado");
                        double mT = rs.getDouble("montoTotal");
                        ped = new pedido(idped, idprove, fecha, est, mT);

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ped;

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
                int est = rs.getInt("estado");
                double mT = rs.getDouble("montoTotal");
                pedido p = new pedido(idped, idprove, fecha, est, mT);
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

        String sql = "Update pedidos set id_proveedor = ? , fechaEmision = ?, montoTotal = ?, estado = ? "
                + "where id_pedidos = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idprov);
            stm.setString(2, this.fechaEmision);
            stm.setDouble(3, this.montoTotal);
            stm.setInt(4, this.estado);
            stm.setInt(5, this.id);

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

    public ArrayList Filtrar(VistaBuscarPedidosController ct) {
        ArrayList<pedido> pedidos = new ArrayList<>();
        String sql = String.valueOf(ct.query);
        pedido ped = new pedido();
        int c = 1;
        System.out.println(sql);
        try (
                Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {
                System.out.println(sql);
            if (ct.k1) {
                pst.setInt(c, ct.desdeIdParse);
                c += 1;
                pst.setInt(c, ct.hastaIdParse);
                c += 1;
            }
            
            if (ct.k2) {
                pst.setString(c, ct.desdeFechaParse);
                c += 1;
                pst.setString(c, ct.hastaFechaParse);
                c += 1;
            }
            
            if (ct.k3) {
                pst.setInt(c, ct.idProvParse);
                c += 1;
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {

                    int idped = rs.getInt("id_pedidos");
                    int idprove = rs.getInt("id_proveedor");
                    String fecha = rs.getString("fechaEmision");
                    int est = rs.getInt("estado");
                    double mT = rs.getDouble("montoTotal");
                    ped = new pedido(idped, idprove, fecha, est, mT);
                    pedidos.add(ped);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pedidos;
    }

}
