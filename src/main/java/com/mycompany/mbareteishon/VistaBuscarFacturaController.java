/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.cliente;
import com.mycompany.mbareteishon.modelo.empleado;
import com.mycompany.mbareteishon.modelo.factura;
import com.mycompany.mbareteishon.modelo.pedido;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VistaBuscarFacturaController implements Initializable {

    @FXML
    private CheckBox boxAllId;
    @FXML
    private TextField txtDesdeId;
    @FXML
    private TextField txtHastaId;
    @FXML
    private CheckBox boxAllFecha;
    @FXML
    private DatePicker dPDesde;
    @FXML
    private DatePicker dPHasta;
    @FXML
    private CheckBox boxAllCl;
    @FXML
    private TextField txtCl;
    @FXML
    private CheckBox boxActivo;
    @FXML
    private CheckBox boxAnulado;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnBuscarCl;
    @FXML
    private CheckBox boxEfectivo;
    @FXML
    private CheckBox boxCredito;
    @FXML
    private CheckBox boxMostrador;
    @FXML
    private CheckBox boxDelivery;
    @FXML
    private TextField txtEmp;
    @FXML
    private Button btnBuscarEmp;
    @FXML
    private CheckBox boxAllEmp;
    @FXML
    private TableView<factura> tblFactura;
    @FXML
    private TableColumn<factura, Integer> colNro;
    @FXML
    private TableColumn<factura, Timestamp> colEmision;
    @FXML
    private TableColumn<factura, String> colCliente;
    @FXML
    private TableColumn<factura, String> colVendedor;
    @FXML
    private TableColumn<factura, String> colFP;
    @FXML
    private TableColumn<factura, String> colTV;
    @FXML
    private TableColumn<factura, String> colMonto;
    @FXML
    private TableColumn<factura, String> colActivo;
    @FXML
    private Button btnEnviar;

    public StringBuilder query = new StringBuilder("SELECT * FROM pedidos WHERE 1");

    public boolean k1 = false, k2 = false, k3 = false, k4 = false;

    public int desdeIdParse, hastaIdParse;
    public String desdeFechaParse, hastaFechaParse;
    public int idClParse;
    public int idEmpParse;
    public int estParse;
    public int tipParse;
    public int modParse;

    ObservableList<factura> lista = FXCollections.observableArrayList();

    private Stage stage;
    private VistaVentasController controladorVentas;
    private Stage primaryStage;
    VistaBuscarClientesController controladorDestinoC;
    VistaBuscarEmpleadosController controladorDestinoE;

    factura fac = new factura();
    empleado emp = new empleado();
    cliente cl = new cliente();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnEnviar.setDisable(true);
        boxAllFecha.setSelected(true);
        boxAllId.setSelected(true);
        dPDesde.setValue(LocalDate.now());
        dPHasta.setValue(LocalDate.now());
        dPDesde.setDisable(true);
        dPHasta.setDisable(true);
        txtDesdeId.setDisable(true);
        txtHastaId.setDisable(true);
        boxAllCl.setSelected(true);
        txtCl.setDisable(true);
        btnBuscarCl.setDisable(true);
        boxAllEmp.setSelected(true);
        txtEmp.setDisable(true);
        btnBuscarEmp.setDisable(true);

        colNro.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));;
        colEmision.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));;
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCl"));;
        colVendedor.setCellValueFactory(new PropertyValueFactory<>("nombreEm"));;
        colFP.setCellValueFactory(new PropertyValueFactory<>("formaPago"));;
        colTV.setCellValueFactory(new PropertyValueFactory<>("tipoVenta"));;
        colMonto.setCellValueFactory(new PropertyValueFactory<>("totalGral"));;
        colActivo.setCellValueFactory(new PropertyValueFactory<>("estadoStr"));;

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void checkAllId(ActionEvent event) {

        if (boxAllId.isSelected()) {
            txtDesdeId.setDisable(true);
            txtHastaId.setDisable(true);
            txtDesdeId.setText(String.valueOf(fac.getPrimeraFactura()));
            txtHastaId.setText(String.valueOf(fac.getUltimaFactura()));
        } else {
            txtDesdeId.setDisable(false);
            txtHastaId.setDisable(false);
            txtDesdeId.setText(String.valueOf(fac.getPrimeraFactura()));
            txtHastaId.setText(String.valueOf(fac.getUltimaFactura()));
        }

    }

    @FXML
    private void checkAllFecha(ActionEvent event) {

        if (boxAllFecha.isSelected()) {
            dPDesde.setDisable(true);
            dPHasta.setDisable(true);
            dPDesde.setValue(LocalDate.now());
            dPHasta.setValue(LocalDate.now());
        } else {
            dPDesde.setDisable(false);
            dPHasta.setDisable(false);
            dPDesde.setValue(LocalDate.now());
            dPHasta.setValue(LocalDate.now());
        }

    }

    @FXML
    private void checkAllCl(ActionEvent event) {

        if (boxAllCl.isSelected()) {
            btnBuscarCl.setDisable(true);
        } else {
            btnBuscarCl.setDisable(false);
        }

    }

    @FXML
    private void aceptar(ActionEvent event) {

        filtrarCon();
        lista = FXCollections.observableArrayList(fac.Filtrar(this));
        mostrarLista();

    }

    @FXML
    private void cancelar(ActionEvent event) {

        controladorVentas.fac = new factura();
        fac.setNumeroFactura(-1);
        primaryStage.close();

    }

    @FXML
    private void buscarCl(ActionEvent event) {
        abrirFxmlModal("vistaBuscarClientes.fxml", "Buscar Cliente");
    }

    public void setClienteSeleccionado(cliente cl) {
        this.cl = cl;
        if (cl.getIdCliente() != -1) {
            mostrarLista();
        }
    }
    public void setEmpleadoSeleccionado(empleado emp){
        this.emp = emp;
        if (emp.getIdEmpleado()!= -1) {
            mostrarLista();
        }
    }

    public void mostrarLista() {

        tblFactura.setItems(lista);

    }

    public void filtrarCon() {

        k1 = false;
        k2 = false;
        k3 = false;
        k4 = false;

        query = new StringBuilder("SELECT * FROM factura f "
                + "INNER JOIN cliente c ON "
                + "f.id_cliente = c.id_cliente "
                + "INNER JOIN empleado e ON "
                + "f.id_empleado = e.id_empleado "
                + "WHERE 1");
        /*
        query = new StringBuilder("SELECT * FROM pedidos p "
            + "INNER JOIN proveedor pro ON " +
            "p.id_proveedor = pro.id_proveedor WHERE 1");
        */

        if (!boxAllId.isSelected()) {
            desdeIdParse = Integer.parseInt(txtDesdeId.getText());
            hastaIdParse = Integer.parseInt(txtHastaId.getText());
            query.append(" AND numero_factura BETWEEN ? AND ?");
            k1 = true;
        }
        if (!boxAllFecha.isSelected()) {
            desdeFechaParse = String.valueOf(dPDesde.getValue());
            hastaFechaParse = String.valueOf(dPHasta.getValue());
            query.append(" AND fecha_emision BETWEEN ? AND ?");
            k2 = true;
        }
        if (!boxAllCl.isSelected()) {
            idClParse = cl.getIdCliente();
            query.append(" AND id_cliente = ?");
            k3 = true;
        }
        if (!boxAllEmp.isSelected()) {
            idEmpParse = emp.getIdEmpleado();
            query.append(" AND id_empleado = ?");
            k4 = true;
        }
        if (boxActivo.isSelected()) {
            query.append(" AND activo = 1");
            estParse = 1;
        }
        if (boxAnulado.isSelected()) {
            query.append(" AND activo = 0");
            estParse = 2;
        }
        if (boxEfectivo.isSelected()) {
            query.append(" AND forma_pago = \"Efectivo\"");
            modParse = 1;
        }
        if (boxCredito.isSelected()) {
            query.append(" AND forma_pago = \"Credito\"");
            modParse = 2;
        }
        if (boxMostrador.isSelected()) {
            query.append(" AND tipo_venta = \"Mostrador\"");
            tipParse = 1;
        }
        if (boxDelivery.isSelected()) {
            query.append(" AND tipo_venta = \"Delivery\"");
            tipParse = 2;
        }

    }

    @FXML
    private void buscarEmp(ActionEvent event) {
        
        abrirFxmlModal("vistaBuscarEmpleados.fxml", "Buscar Empleado");
    }

    @FXML
    private void checkAllEmp(ActionEvent event) {
        if (boxAllEmp.isSelected()) {
            btnBuscarEmp.setDisable(true);
        } else {
            btnBuscarEmp.setDisable(false);
        }
    }

    private void noMostrarFila(MouseEvent event) {

        btnEnviar.setDisable(true);

    }

    @FXML
    private void mostrarFila(MouseEvent event) {

        btnEnviar.setDisable(false);

    }

    @FXML
    private void enviar(ActionEvent event) {

        if (fac.getNumeroFactura() != 0) {

            controladorVentas.fac = fac;
            controladorVentas.mostrarFactura();

        }

        fac = tblFactura.getSelectionModel().getSelectedItem();
        if (fac != null && controladorVentas != null) {
            controladorVentas.setFacturaSeleccionada(fac);
        } else {
            fac = new factura();
            fac.setNumeroFactura(-1);
            controladorVentas.setFacturaSeleccionada(fac);
        }

        primaryStage.close();

    }

    @FXML
    private void checkActivo(ActionEvent event) {
    }

    @FXML
    private void checkAnulado(ActionEvent event) {
    }

    @FXML
    private void checkEfectivo(ActionEvent event) {
    }

    @FXML
    private void checkCredito(ActionEvent event) {
    }

    @FXML
    private void checkMostrador(ActionEvent event) {
    }

    @FXML
    private void checkDelivery(ActionEvent event) {
    }

    public void abrirFxmlModal(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Obtener el controlador del destino
            if (titulo.equals("Buscar Cliente")) {

                controladorDestinoC = loader.getController();

            } else if (titulo.equals("Buscar Empleado")) {

                controladorDestinoE = loader.getController();
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false); // No permitir redimensionar

            // Hacer que la ventana sea modal y bloquear la interacción con la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);

            // Pasar el Stage y el controlador de origen al controlador de destino
            if (titulo.equals("Buscar Cliente")) {

                controladorDestinoC.setStage(stage);
                controladorDestinoC.setControladorBuscarFactura(this);

            } else if (titulo.equals("Buscar Empleado")) {

                controladorDestinoE.setStage(stage);
                controladorDestinoE.setControladorBuscarFactura(this);
            }

            // Mostrar y esperar a que se cierre
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(VistaPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarCliente() {
        txtCl.setText(cl.getNombreCliente() + " " + cl.getApellidoCliente());
    }

    public void mostrarEmpleado() {
        txtEmp.setText(emp.getNombre() + " " + emp.getApellido());
    }

    public void setControladorVentas(VistaVentasController controladorVentas) {
        this.controladorVentas = controladorVentas;
    }

    @FXML
    private void limpiar(ActionEvent event) {

        txtCl.clear();
        txtEmp.clear();
        boxAllFecha.setSelected(true);
        boxAllId.setSelected(true);
        dPDesde.setValue(LocalDate.now());
        dPHasta.setValue(LocalDate.now());
        dPDesde.setDisable(true);
        dPHasta.setDisable(true);
        txtDesdeId.setDisable(true);
        txtHastaId.setDisable(true);
        boxAllCl.setSelected(true);
        txtCl.setDisable(true);
        btnBuscarCl.setDisable(true);
        boxAllEmp.setSelected(true);
        txtEmp.setDisable(true);
        btnBuscarEmp.setDisable(true);

    }

}
