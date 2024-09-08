/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mbareteishon.modelo;

import com.mycompany.mbareteishon.VistaBuscarFacturaController;
import com.mycompany.mbareteishon.VistaBuscarPedidosController;
import com.mycompany.mbareteishon.clases.conexion;
import com.mycompany.mbareteishon.clases.sentencias;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nahue
 */
public class factura extends conexion implements sentencias {

    private Timestamp fechaEmision;
    private int numeroFactura;
    private int idCliente;
    private int idEmpleado;
    private double totalGral;
    private String formaPago;
    private String tipoVenta;
    private int activo;
    private double iva10;

    public factura() {
    }

    public factura(Timestamp fechaEmision, int numeroFactura, int idCliente, int idEmpleado, double totalGral, String formaPago, String tipoVenta, int activo, double iva10) {
        this.fechaEmision = fechaEmision;
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.totalGral = totalGral;
        this.formaPago = formaPago;
        this.tipoVenta = tipoVenta;
        this.activo = activo;
        this.iva10 = iva10;
    }

    @Override
    public boolean insertar() {

        String sql = "insert into factura(fecha_emision, numero_factura, id_cliente, id_empleado, total_gral, forma_pago, tipo_venta, activo, iva_10) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setTimestamp(1, this.fechaEmision);
            stm.setInt(2, this.numeroFactura);
            stm.setInt(3, this.idCliente);
            stm.setInt(4, this.idEmpleado);
            stm.setDouble(5, this.totalGral);
            stm.setString(6, this.formaPago);
            stm.setString(7, this.tipoVenta);
            stm.setInt(8, this.activo);
            stm.setDouble(9, this.iva10);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    public int getPrimeraFactura() {
        int menor = 0;
        String sql = "select MIN(numero_factura) as primera_factura from factura";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                menor = rs.getInt("primera_factura");

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return menor;
    }

    public int getUltimaFactura() {

        int mayor = 0;
        String sql = "select MAX(numero_factura) as ultima_factura from factura";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                mayor = rs.getInt("ultima_factura");

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return mayor;

    }

    public factura getFacturaFromDb(int paramId) {
        factura f = new factura();

        if (paramId != 0) {

            String sql = "select * from factura where numero_factura = ?";

            try (
                    Connection con = getCon(); PreparedStatement pst = con.prepareStatement(sql);) {

                pst.setInt(1, paramId);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {

                        Timestamp fa = rs.getTimestamp("fecha_emision");
                        int n = rs.getInt("numero_factura");
                        int ic = rs.getInt("id_cliente");
                        int ie = rs.getInt("id_empleado");
                        double tg = rs.getDouble("total_gral");
                        String fp = rs.getString("forma_pago");
                        String tp = rs.getString("tipo_venta");
                        int a = rs.getInt("activo");
                        double i10 = rs.getDouble("iva_10");

                        f = new factura(fa, n, ic, ie, tg, fp, tp, a, i10);

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return f;

    }

    @Override
    public ArrayList consulta() {

        ArrayList<factura> facturas = new ArrayList<>();
        factura f = new factura();
        String sql = "select * from factura";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {

                Timestamp fa = rs.getTimestamp("fecha_emision");
                int n = rs.getInt("numero_factura");
                int ic = rs.getInt("id_cliente");
                int ie = rs.getInt("id_empleado");
                double tg = rs.getDouble("total_gral");
                String fp = rs.getString("forma_pago");
                String tp = rs.getString("tipo_venta");
                int a = rs.getInt("activo");
                double i10 = rs.getDouble("iva_10");

                f = new factura(fa, n, ic, ie, tg, fp, tp, a, i10);
                facturas.add(f);

            }

        } catch (SQLException ex) {
            System.out.println("lol");
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);

        }

        return facturas;

    }

    @Override
    public boolean modificar() {

        String sql = "Update factura set fecha_emision = ?, id_cliente = ?, id_empleado = ?, total_gral = ?, forma_pago = ?, tipo_venta = ?, activo = ?, iva_10 = ? "
                + "where numero_factura = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {

            stm.setTimestamp(1, this.fechaEmision);
            stm.setInt(2, this.idCliente);
            stm.setInt(3, this.idEmpleado);
            stm.setDouble(4, this.totalGral);
            stm.setString(5, this.formaPago);
            stm.setString(6, this.tipoVenta);
            stm.setInt(7, this.activo);
            stm.setDouble(8, this.iva10);
            stm.setInt(9, this.numeroFactura);
            stm.executeUpdate();

            int rowsUpdated = stm.executeUpdate();

            // Verificar si se ha actualizado al menos una fila
            if (rowsUpdated > 0) {
                return true;  // La modificación fue exitosa
            } else {
                return false; // No se realizó ninguna modificación
            }

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("bruh");
            return false;

        }

    }

    @Override
    public boolean eliminar() {

        String sql = "Delete from factura where numero_factura = ?";

        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.numeroFactura);
            stm.executeUpdate();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }

    }

    public ArrayList Filtrar(VistaBuscarFacturaController ct) {
        ArrayList<factura> facturas = new ArrayList<>();
        String sql = String.valueOf(ct.query);
        factura fac = new factura();
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
                pst.setInt(c, ct.idClParse);
                c += 1;
            }

            if (ct.k4) {
                pst.setInt(c, ct.idEmpParse);
                c += 1;
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {

                    Timestamp fa = rs.getTimestamp("fecha_emision");
                    int n = rs.getInt("numero_factura");
                    int ic = rs.getInt("id_cliente");
                    int ie = rs.getInt("id_empleado");
                    double tg = rs.getDouble("total_gral");
                    String fp = rs.getString("forma_pago");
                    String tp = rs.getString("tipo_venta");
                    int a = rs.getInt("activo");
                    double i10 = rs.getDouble("iva_10");

                    fac = new factura(fa, n, ic, ie, tg, fp, tp, a, i10);
                    facturas.add(fac);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return facturas;
    }

    public Timestamp getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Timestamp fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getTotalGral() {
        return totalGral;
    }

    public void setTotalGral(double totalGral) {
        this.totalGral = totalGral;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public double getIva10() {
        return iva10;
    }

    public void setIva10(double iva10) {
        this.iva10 = iva10;
    }

}
