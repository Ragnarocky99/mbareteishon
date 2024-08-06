/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.detallePedido;
import com.mycompany.mbareteishon.modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaPedidosController implements Initializable {

    @FXML
    private TextField txtEmision;
    @FXML
    private TextField txtIdPedido;
    @FXML
    private TextField txtProveedor;
    @FXML
    private Button btnBuscarProveedor;
    @FXML
    private TableColumn<detallePedido, Integer> colNo;
    @FXML
    private TableView<detallePedido> tblDetallePedido;
    @FXML
    private TableColumn<detallePedido, Integer> colIdProducto;
    @FXML
    private TableColumn<detallePedido, String> colNombreProducto;
    @FXML
    private TableColumn<detallePedido, Double> colCosto;
    @FXML
    private TableColumn<detallePedido, Integer> colCantidad;
    @FXML
    private TableColumn<detallePedido, Double> colCostoTotal;
    @FXML
    private TextField txtTotal;
    @FXML
    private Button btnModificarDetalle;
    @FXML
    private Button btnEliminarDetalle;
    @FXML
    private Button btnAgregarDetalle;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private TextField txtCodigoProducto;
    @FXML
    private TextField txtCantidadProducto;
    @FXML
    private TextField txtCostoProducto;
    @FXML
    private Button btnAceptarProducto;
    @FXML
    private Button btnCancelarProducto;
    @FXML
    private Button btnGuardarPedido;
    @FXML
    private Button btnCancelarPedido;
    @FXML
    private Button btnNuevoPedido;
    @FXML
    private Button btnModificarPedido;
    @FXML
    private Button btnAnularPedido;
    /**
     * Initializes the controller class.
     */

    public producto pro = new producto();

    private Stage primaryStage;

    private int c = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        c = 0;

        this.primaryStage = primaryStage;

        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        // Formatear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        // Mostrar la fecha en el label
        txtEmision.setText(formattedDate);

        colNo.setCellValueFactory(new PropertyValueFactory<>("idPed"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idPro"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCostoTotal.setCellValueFactory(new PropertyValueFactory<>("costoTotal"));

        txtEmision.setDisable(true);
        txtIdPedido.setDisable(true);
        txtProveedor.setDisable(true);
        btnBuscarProveedor.setDisable(true);
        tblDetallePedido.setDisable(true);
        btnAgregarDetalle.setDisable(true);
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        txtTotal.setDisable(true);
        txtCodigoProducto.setDisable(true);
        btnBuscarProducto.setDisable(true);
        txtCantidadProducto.setDisable(true);
        txtCostoProducto.setDisable(true);
        btnAceptarProducto.setDisable(true);
        btnCancelarProducto.setDisable(true);
        btnNuevoPedido.setDisable(false);
        btnModificarPedido.setDisable(true);
        btnGuardarPedido.setDisable(true);
        btnAnularPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);

    }

    @FXML
    private void goToSearchProveedor(ActionEvent event) {

        abrirFxmlModal("vistaBuscarProveedor.fxml", "Buscar Proveedor");

    }

    @FXML
    private void modificarProducto(ActionEvent event) {
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
    }

    @FXML
    private void agregarProducto(ActionEvent event) {

        btnAgregarDetalle.requestFocus();

    }

    @FXML
    private void goToSearchProducto(ActionEvent event) {

        abrirFxmlModal("vistaBuscarArticulos.fxml", "Buscar Producto");

    }

    @FXML
    private void aceptarProducto(ActionEvent event) {

        if ("".equals(txtCodigoProducto.getText())) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("No se ha seleccionado un producto");
            alerta.showAndWait();
        } else if (Integer.parseInt(txtCantidadProducto.getText()) < 0) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("Cantidad menor o igual a cero");
            alerta.showAndWait();

        } else {
            c += 1;
            int cant = Integer.parseInt(txtCantidadProducto.getText());
            double cost = Double.parseDouble(txtCostoProducto.getText());
            double total = cant * cost;
            detallePedido det = new detallePedido(pro.getId(), pro.getDesc(), Integer.parseInt(txtCantidadProducto.getText()), Double.parseDouble(txtCostoProducto.getText()), total, c);
            tblDetallePedido.getItems().add(det);
            txtCantidadProducto.clear();
            txtCodigoProducto.clear();
            txtCostoProducto.clear();
            btnBuscarProducto.requestFocus();
        }

    }

    @FXML
    private void cancelarProducto(ActionEvent event) {

        txtCantidadProducto.clear();
        txtCodigoProducto.clear();
        txtCostoProducto.clear();

    }

    @FXML
    private void guardarPedido(ActionEvent event) {
    }

    @FXML
    private void cancelarPedido(ActionEvent event) {

        btnAgregarDetalle.setFocusTraversable(true);

        txtIdPedido.clear();
        txtProveedor.clear();
        tblDetallePedido.setItems(FXCollections.observableArrayList());
        txtTotal.clear();
        txtCodigoProducto.clear();
        txtCantidadProducto.clear();
        txtCostoProducto.clear();

        txtEmision.setDisable(true);
        txtIdPedido.setDisable(true);
        txtProveedor.setDisable(true);
        btnBuscarProveedor.setDisable(true);
        tblDetallePedido.setDisable(true);
        btnAgregarDetalle.setDisable(true);
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        txtTotal.setDisable(true);
        txtCodigoProducto.setDisable(true);
        btnBuscarProducto.setDisable(true);
        txtCantidadProducto.setDisable(true);
        txtCostoProducto.setDisable(true);
        btnAceptarProducto.setDisable(true);
        btnCancelarProducto.setDisable(true);
        btnNuevoPedido.setDisable(false);
        btnModificarPedido.setDisable(true);
        btnGuardarPedido.setDisable(true);
        btnAnularPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);

    }

    @FXML
    private void nuevoPedido(ActionEvent event) {

        c = 0;
        txtEmision.setDisable(true);
        txtIdPedido.setDisable(true);
        txtProveedor.setDisable(true);
        btnBuscarProveedor.setDisable(false);
        tblDetallePedido.setDisable(false);
        btnAgregarDetalle.setDisable(false);
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        txtTotal.setDisable(true);
        txtCodigoProducto.setDisable(true);
        btnBuscarProducto.setDisable(false);
        txtCantidadProducto.setDisable(false);
        txtCostoProducto.setDisable(true);
        btnAceptarProducto.setDisable(false);
        btnCancelarProducto.setDisable(false);
        btnNuevoPedido.setDisable(true);
        btnModificarPedido.setDisable(true);
        btnGuardarPedido.setDisable(false);
        btnAnularPedido.setDisable(true);
        btnCancelarPedido.setDisable(false);

    }

    @FXML
    private void modificarPedido(ActionEvent event) {
    }

    @FXML
    private void anularPedido(ActionEvent event) {
    }

    public void setProductoSeleccionado(producto pro) {
        this.pro = pro;
        if (pro.getId() != -1) {
            txtCodigoProducto.setText(String.valueOf(pro.getId()));
            txtCostoProducto.setText(String.valueOf(pro.getCosto()));
        }
    }

    public void abrirFxmlModal(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Obtener el controlador del destino
            VistaBuscarArticulosController controladorDestino = loader.getController();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false); // No permitir redimensionar

            // Hacer que la ventana sea modal y bloquear la interacción con la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);

            // Pasar el Stage y el controlador de origen al controlador de destino
            controladorDestino.setStage(stage);
            controladorDestino.setControladorPedidos(this);

            // Mostrar y esperar a que se cierre
            stage.showAndWait();

            if (pro.getId() == -1) {

                txtCodigoProducto.clear();
                txtCostoProducto.clear();

            } else {

                txtCostoProducto.setDisable(false);

            }

        } catch (IOException ex) {
            Logger.getLogger(VistaPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
    }
}
