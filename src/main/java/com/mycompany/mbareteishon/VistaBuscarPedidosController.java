/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.VistaPedidosController;
import com.mycompany.mbareteishon.modelo.pedido;
import com.mycompany.mbareteishon.modelo.proveedor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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

/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaBuscarPedidosController implements Initializable {

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
    private CheckBox boxAllProv;
    @FXML
    private TextField txtProv;
    @FXML
    private CheckBox boxActivo;
    @FXML
    private CheckBox boxAnulado;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnBuscarProv;
    @FXML
    private TableView<pedido> tblPedidos;
    @FXML
    private TableColumn<pedido, Integer> colId;
    @FXML
    private TableColumn<pedido, Integer> colProv;
    @FXML
    private TableColumn<pedido, String> colFecha;
    @FXML
    private TableColumn<pedido, Double> colMonto;
    @FXML
    private TableColumn<pedido, Integer> colEstado;

    public boolean k1 = false, k2 = false, k3 = false;

    public StringBuilder query = new StringBuilder("SELECT * FROM pedidos WHERE 1");

    public int desdeIdParse, hastaIdParse;
    public String desdeFechaParse, hastaFechaParse;
    public int idProvParse;
    public int estParse;

    ObservableList<pedido> lista = FXCollections.observableArrayList();

    proveedor prov = new proveedor();

    private Stage primaryStage;
    private Stage stage;
    private VistaPedidosController controladorPedidos;
    private VistaBuscarProveedoresController controladorDestinoP;

    pedido ped = new pedido();
    @FXML
    private Button btnEnviar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        boxAllFecha.setSelected(true);
        boxAllId.setSelected(true);
        boxAllProv.setSelected(true);
        dPDesde.setValue(LocalDate.now());
        dPHasta.setValue(LocalDate.now());
        dPDesde.setDisable(true);
        dPHasta.setDisable(true);
        txtDesdeId.setDisable(true);
        txtHastaId.setDisable(true);
        txtProv.setDisable(true);
        btnBuscarProv.setDisable(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProv.setCellValueFactory(new PropertyValueFactory<>("idprov"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));

    }

    public void setProveedorSeleccionado(proveedor prov) {
        this.prov = prov;
        if (prov.getId()!= -1) {
            mostrarLista();
        }
    }

    public void mostrarProv() {
        txtProv.setText(prov.getNombre());
    }

    @FXML
    private void checkAllId(ActionEvent event) {

        if (boxAllId.isSelected()) {
            txtDesdeId.setDisable(true);
            txtHastaId.setDisable(true);
            txtDesdeId.setText(String.valueOf(ped.getPrimerPedido()));
            txtHastaId.setText(String.valueOf(ped.getUltimoPedido()));
        } else {
            txtDesdeId.setDisable(false);
            txtHastaId.setDisable(false);
            txtDesdeId.setText(String.valueOf(ped.getPrimerPedido()));
            txtHastaId.setText(String.valueOf(ped.getUltimoPedido()));
        }

    }

    @FXML
    private void checkActivo(ActionEvent event) {

        boxAnulado.setSelected(false);

    }

    @FXML
    private void checkAnulado(ActionEvent event) {

        boxActivo.setSelected(false);

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
    private void checkAllProv(ActionEvent event) {

        if (boxAllProv.isSelected()) {
            btnBuscarProv.setDisable(true);
        } else {
            btnBuscarProv.setDisable(false);
        }

    }

    @FXML
    private void aceptar(ActionEvent event) {

        filtrarCon();
        lista = FXCollections.observableArrayList(ped.Filtrar(this));
        mostrarLista();

    }

    public void filtrarCon() {

        k1 = false;
        k2 = false;
        k3 = false;

        query = new StringBuilder("SELECT * FROM pedidos WHERE 1");

        if (!boxAllId.isSelected()) {
            System.out.println("o");
            desdeIdParse = Integer.parseInt(txtDesdeId.getText());
            hastaIdParse = Integer.parseInt(txtHastaId.getText());
            query.append(" AND id_pedidos BETWEEN ? AND ?");
            k1 = true;
        }
        if (!boxAllFecha.isSelected()) {
            desdeFechaParse = String.valueOf(dPDesde.getValue());
            hastaFechaParse = String.valueOf(dPHasta.getValue());
            query.append(" AND fechaEmision BETWEEN ? AND ?");
            k2 = true;
        }
        if (!boxAllProv.isSelected()) {
            idProvParse = prov.getId();
            query.append(" AND id_proveedor = ?");
            k3 = true;
        }
        if (boxActivo.isSelected()) {
            query.append(" AND estado = 1");
            estParse = 1;
        }
        if (boxAnulado.isSelected()) {
            query.append(" AND estado = 0");
            estParse = 2;
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setControladorPedidos(VistaPedidosController controladorPedidos) {
        this.controladorPedidos = controladorPedidos;
    }

    public void mostrarLista() {

        tblPedidos.setItems(lista);

    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void buscarProv(ActionEvent event) {
        abrirFxmlModal("vistaBuscarProveedores.fxml", "Buscar Proveedor");
    }

    @FXML
    private void noMostrarFila(MouseEvent event) {

        ped = new pedido();
        ped.setId(-1);

    }

    public void abrirFxmlModal(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Obtener el controlador del destino
            if (titulo.equals("Buscar Proveedor")) {

                controladorDestinoP = loader.getController();

            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false); // No permitir redimensionar

            // Hacer que la ventana sea modal y bloquear la interacción con la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);

            // Pasar el Stage y el controlador de origen al controlador de destino
            if (titulo.equals("Buscar Proveedor")) {

                controladorDestinoP.setStage(stage);
                controladorDestinoP.setControladorBpedidos(this);

            }

            // Mostrar y esperar a que se cierre
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(VistaPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
    }

    @FXML
    private void enviar(ActionEvent event) {

        ped = tblPedidos.getSelectionModel().getSelectedItem();
        if (ped != null && controladorPedidos != null) {
            controladorPedidos.setPedidoSeleccionado(ped);
        } else {
            ped.setId(-1);
            controladorPedidos.setPedidoSeleccionado(ped);
        }

        stage.close();
    }

}
