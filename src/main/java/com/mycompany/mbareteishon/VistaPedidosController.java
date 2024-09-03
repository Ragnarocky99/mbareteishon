/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.detallePedido;
import com.mycompany.mbareteishon.modelo.pedido;
import com.mycompany.mbareteishon.modelo.producto;
import com.mycompany.mbareteishon.modelo.proveedor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
    private TableView<detallePedido> tblDetallePedido;
    @FXML
    private TableColumn<detallePedido, Integer> colIdProducto;
    @FXML
    private TableColumn<producto, String> colNombreProducto;
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
    private Button btnAnularPedido;
    @FXML
    private TextField txtRuc;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnBuscarPedido;
    @FXML
    private Button btnSgte;

    int pos;

    int c = 0;

    int totalPedido;

    boolean modificar;

    private Stage primaryStage;

    producto pro = new producto();

    proveedor prov = new proveedor();

    pedido ped = new pedido();

    detallePedido det = new detallePedido();

    VistaBuscarPedidosController controladorDestinoPed;
    VistaBuscarArticulosController controladorDestinoA;
    VistaBuscarProveedoresController controladorDestinoP;

    ObservableList<detallePedido> lista = FXCollections.observableArrayList();
    ObservableList<detallePedido> listaAux = FXCollections.observableArrayList();
    @FXML
    private CheckBox chkActivo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.primaryStage = primaryStage;

        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idPro"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCostoTotal.setCellValueFactory(new PropertyValueFactory<>("costoTotal"));

        ped = new pedido();
        ped.setId(ped.getUltimoPedido());
        ped = ped.getPedido(ped.getId());

        mostrarPedido();
        inicializarPedido();
    }

    public void inicializarPedido() {
        //Siempre Disable
        txtEmision.setDisable(true);
        txtIdPedido.setDisable(true);
        txtProveedor.setDisable(true);
        txtRuc.setDisable(true);
        chkActivo.setDisable(true);
        txtCodigoProducto.setDisable(true);
        txtCostoProducto.setDisable(true);
        txtTotal.setDisable(true);
        //
        tblDetallePedido.setDisable(true);
        btnBuscarProveedor.setDisable(true);
        btnAgregarDetalle.setDisable(true);
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnBuscarProducto.setDisable(true);
        txtCantidadProducto.setDisable(true);
        btnAceptarProducto.setDisable(true);
        btnCancelarProducto.setDisable(true);
        btnNuevoPedido.setDisable(false);
        btnGuardarPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);
        btnAtras.setDisable(false);
        btnSgte.setDisable(false);
        btnBuscarPedido.setDisable(false);

        if (ped.getId() == 0 || ped.getEstado() == 0) {

            btnAnularPedido.setDisable(true);

        } else if (ped.getId() != 0 || ped.getEstado() != 0) {

            btnAnularPedido.setDisable(false);

        }

    }

    public void ejecutarPedido() {

        tblDetallePedido.setDisable(false);
        btnBuscarProveedor.setDisable(false);
        btnAgregarDetalle.setDisable(false);
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnBuscarProducto.setDisable(true);
        txtCantidadProducto.setDisable(true);
        btnAceptarProducto.setDisable(true);
        btnCancelarProducto.setDisable(true);
        btnNuevoPedido.setDisable(true);
        btnGuardarPedido.setDisable(false);
        btnCancelarPedido.setDisable(false);
        btnAnularPedido.setDisable(true);
        btnAtras.setDisable(true);
        btnSgte.setDisable(true);
        btnBuscarPedido.setDisable(true);

    }

    public void aggPro() {

        tblDetallePedido.setDisable(true);
        btnBuscarProveedor.setDisable(true);
        btnAgregarDetalle.setDisable(true);
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);
        btnBuscarProducto.setDisable(false);

        btnBuscarProducto.requestFocus();

        txtCantidadProducto.setDisable(false);
        btnAceptarProducto.setDisable(false);
        btnCancelarProducto.setDisable(false);
        btnNuevoPedido.setDisable(true);
        btnGuardarPedido.setDisable(true);
        btnCancelarPedido.setDisable(true);

    }

    @FXML
    private void modificarProducto(ActionEvent event) {

        modificar = true;
        det = tblDetallePedido.getSelectionModel().getSelectedItem();
        pos = det.getNro();
        txtCodigoProducto.setText(String.valueOf(det.getIdPro()));
        txtCantidadProducto.setText(String.valueOf(det.getCantidad()));
        txtCostoProducto.setText(String.valueOf(det.getCosto()));
        aggPro();

    }

    @FXML
    private void eliminarProducto(ActionEvent event) {

        det = tblDetallePedido.getSelectionModel().getSelectedItem();
        totalPedido = 0;

        lista.remove(det);
        c = 1;
        for (detallePedido det : lista) {
            det.setIdPed(c);
            c += 1;
        }
        for (detallePedido det : lista) {
            totalPedido += det.getCostoTotal();
        }
        txtTotal.setText(String.valueOf(totalPedido));
        tblDetallePedido.setItems(lista);
        c = 0;
        ejecutarPedido();

    }

    @FXML
    private void agregarProducto(ActionEvent event) {

        modificar = false;
        aggPro();

    }

    @FXML
    private void aceptarProducto(ActionEvent event) {

        if (!modificar) {

            if ("".equals(txtCodigoProducto.getText())) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("");
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha seleccionado un producto");
                alerta.showAndWait();
            } else if (Integer.parseInt(txtCantidadProducto.getText()) <= 0) {

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("");
                alerta.setHeaderText(null);
                alerta.setContentText("Cantidad menor o igual a cero");
                alerta.showAndWait();

            } else {

                System.out.println("Seleccionado para agregar : " + pro.getId());
                c = lista.size();
                int cant = Integer.parseInt(txtCantidadProducto.getText());
                double cost = Double.parseDouble(txtCostoProducto.getText());
                double total = cant * cost;
                det = new detallePedido(c + 1, pro.getId(), cant, cost, total, Integer.parseInt(txtIdPedido.getText()), pro.getNombre());
                if (comprobarProdRepetido()) {

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("El sistema comunica;");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Se detecto una repeticion de productos en \n"
                            + "ID : " + det.getIdPro() + " Nombre : " + det.getNombre() + "\n"
                            + "Desea combinar sus cantidades?");
                    Optional<ButtonType> opcion = alerta.showAndWait();
                    if (opcion.get() == ButtonType.OK) {

                        System.out.println("Elemento a modificar : " + det.getNro() + " ID" + det.getIdPro());
                        System.out.println("En el array " + (det.getNro() - 1));
                        System.out.println("Tamaño del array " + lista.size());
                        System.out.println("------------------Array--------------------");
                        c = 0;
                        for (detallePedido pedido : lista) {

                            pedido.setNro(c + 1);
                            c += 1;
                            System.out.println("Elemento " + (pedido.getNro() - 1) + " ID" + pedido.getIdPro());

                        }
                        lista.set(det.getNro() - 1, det);
                        tblDetallePedido.setItems(lista);

                    }

                } else {

                    lista.add(det);
                    tblDetallePedido.setItems(lista);

                }

                System.out.println("------------------Nuevo Array--------------------");
                for (detallePedido pedido : lista) {

                    System.out.println("Elemento " + (pedido.getNro() - 1) + " ID" + pedido.getIdPro());

                }

            }

        } else {

            if ("".equals(txtCodigoProducto.getText())) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("");
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha seleccionado un producto");
                alerta.showAndWait();
            } else if (Integer.parseInt(txtCantidadProducto.getText()) <= 0) {

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("");
                alerta.setHeaderText(null);
                alerta.setContentText("Cantidad menor o igual a cero");
                alerta.showAndWait();

            } else {
                pos = det.getNro();
                pro.setId(Integer.parseInt(txtCodigoProducto.getText()));
                int cant = Integer.parseInt(txtCantidadProducto.getText());
                double cost = Double.parseDouble(txtCostoProducto.getText());
                double total = cant * cost;

                det = new detallePedido(pos, pro.getId(), cant, cost, total, Integer.parseInt(txtIdPedido.getText()), det.consultaNombre(pro.getId()));

                if (comprobarProdRepetido()) {

                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("El sistema comunica;");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Se detecto una repeticion de productos en \n"
                            + "ID : " + det.getIdPro() + " Nombre : " + det.getNombre() + "\n"
                            + "Desea combinar sus cantidades?");
                    Optional<ButtonType> opcion = alerta.showAndWait();
                    if (opcion.get() == ButtonType.OK) {

                        System.out.println("Elemento a modificar : " + det.getNro() + " ID" + det.getIdPro());
                        System.out.println("Elemento a eliminar : " + (pos - 1));
                        System.out.println("En el array " + (det.getNro() - 1));
                        System.out.println("Tamaño del array " + lista.size());
                        System.out.println("Tamaño del array " + lista.size());
                        System.out.println("------------------Array--------------------");
                        for (detallePedido pedido : lista) {

                            System.out.println("Elemento " + (pedido.getNro() - 1) + " ID" + pedido.getIdPro());

                        }

                        lista.set(det.getNro() - 1, det);
                        lista.remove(pos - 1);
                        tblDetallePedido.setItems(lista);

                        System.out.println("------------------Nuevo Array--------------------");
                        c = 0;
                        for (detallePedido pedido : lista) {

                            pedido.setNro(c + 1);
                            c += 1;
                            System.out.println("Elemento " + (pedido.getNro() - 1) + " ID" + pedido.getIdPro());

                        }

                    }

                } else {

                    lista.set(det.getNro() - 1, det);
                    tblDetallePedido.setItems(lista);

                }

            }

        }

        totalPedido = 0;
        for (detallePedido det : lista) {
            totalPedido += det.getCostoTotal();
        }
        txtTotal.setText(String.valueOf(totalPedido));

        txtCodigoProducto.clear();
        txtCantidadProducto.clear();
        txtCostoProducto.clear();
        ejecutarPedido();

    }

    public boolean comprobarProdRepetido() {

        System.out.println("Comprobacion");
        for (detallePedido detalle : lista) {

            System.out.println("Elemento " + detalle.getNro() + " ID " + detalle.getIdPro());

            if ((det.getIdPro() == detalle.getIdPro()) && (det.getNro() != detalle.getNro())) {

                int cant = det.getCantidad() + detalle.getCantidad();
                double cost = detalle.getCosto();
                double total = cant * cost;

                det = new detallePedido(detalle.getNro(), detalle.getIdPro(), cant, cost, total, detalle.getIdPed(), detalle.getNombre());

                return true;

            } else {

                System.out.println("No coincide con " + det.getNro() + " ID " + det.getIdPro());

            }
        }

        return false;

    }

    @FXML
    private void cancelarProducto(ActionEvent event) {

        txtCodigoProducto.clear();
        txtCantidadProducto.clear();
        txtCostoProducto.clear();
        ejecutarPedido();

    }

    @FXML
    private void guardarPedido(ActionEvent event) {

        for (detallePedido pedido : lista) {
            System.out.println(pedido.getNro() + " " + pedido.getIdPro());
        }
        
        if (txtProveedor.getText().equals("")) {

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("");
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha seleccionado un proveedor");
                alerta.showAndWait();
            } else if (lista.isEmpty()) {

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("");
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha insertado ningun producto");
                alerta.showAndWait();

            } else {

                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("El sistema comunica;");
                alerta.setHeaderText(null);
                alerta.setContentText("¿Desea grabar el pedido?");
                Optional<ButtonType> opcion = alerta.showAndWait();
                if (opcion.get() == ButtonType.OK) {

                    ped = new pedido(Integer.parseInt(txtIdPedido.getText()), prov.getId(), String.valueOf(txtEmision.getText()), 1, Double.parseDouble(txtTotal.getText()));
                    if (ped.insertar()) {//insertado

                        for (detallePedido pedido : lista) {

                            det = new detallePedido();
                            det.setIdPed(ped.getId());
                            det.setIdPro(pedido.getIdPro());
                            det.setCantidad(pedido.getCantidad());
                            det.setCosto(pedido.getCosto());
                            det.setCostoTotal(pedido.getCostoTotal());
                            pro.setId(det.getIdPro());
                            if (det.insertar()) {
                                System.out.println("se inserto " + det.getIdPro());
                            } else {
                                System.out.println("no se inserto " + det.getIdPro());
                            }

                        }
                        Alert alertaIn = new Alert(Alert.AlertType.INFORMATION);
                        alertaIn.setTitle("El sistema comunica:");
                        alertaIn.setHeaderText(null);
                        alertaIn.setContentText("Insertado correctamente en la base de datos");
                        alertaIn.show();
                        totalPedido = 0;
                        modificar = false;
                        c = 0;


                        inicializarPedido();
                        mostrarPedido();

                    } else {
                        Alert alertaIn = new Alert(Alert.AlertType.ERROR);
                        alertaIn.setTitle("El sistema comunica:");
                        alertaIn.setHeaderText(null);
                        alertaIn.setContentText("Error. Registro no insertado.");
                        alertaIn.show();
                    }
                }

            }

    }

    @FXML
    private void cancelarPedido(ActionEvent event) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("El sistema comunica;");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Desea cancelar el pedido?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {

            lista.clear();
                totalPedido = 0;

                btnAgregarDetalle.setFocusTraversable(true);

                txtRuc.clear();
                txtIdPedido.clear();
                txtProveedor.clear();
                tblDetallePedido.setItems(FXCollections.observableArrayList());
                txtTotal.clear();
                txtCodigoProducto.clear();
                txtCantidadProducto.clear();
                txtCostoProducto.clear();

                inicializarPedido();
            
        }

    }

    @FXML
    private void nuevoPedido(ActionEvent event) {

        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        // Formatear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        // Mostrar la fecha en el label
        txtEmision.setText(formattedDate);

        lista.clear();
        tblDetallePedido.setItems(lista);
        txtProveedor.clear();
        txtRuc.clear();
        txtIdPedido.setText(String.valueOf(ped.getUltimoPedido() + 1));
        c = 0;

        ejecutarPedido();

    }

    @FXML
    private void anularPedido(ActionEvent event) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("El sistema comunica;");
        alerta.setHeaderText(null);
        alerta.setContentText("Realmente desea anular este pedido?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {

            ped.setEstado(0);
            ped.modificar();

        }
        mostrarPedido();

    }

    @FXML
    private void mostrarFila(MouseEvent event) {

        btnModificarDetalle.setDisable(false);
        btnEliminarDetalle.setDisable(false);

    }

    @FXML
    private void noMostrarFila(MouseEvent event) {

        det = new detallePedido();
        btnModificarDetalle.setDisable(true);
        btnEliminarDetalle.setDisable(true);

    }

    @FXML
    private void atrasPedido(ActionEvent event) {

        pedido pedVacio = new pedido();
        if (!pedVacio.equals(ped.getPedido(ped.getId() - 1))) {

            ped = ped.getPedido(ped.getId() - 1);
            mostrarPedido();

        }
        if (txtIdPedido.getText().equals("0")) {

            ped = ped.getPedido(ped.getUltimoPedido());
            mostrarPedido();
        }
    }

    @FXML
    private void buscarPedido(ActionEvent event) {

        abrirFxmlModal("vistaBuscarPedidos.fxml", "Buscar Pedidos");

    }

    @FXML
    private void sgtePedido(ActionEvent event) {

        pedido pedVacio = new pedido();
        if (!pedVacio.equals(ped.getPedido(ped.getId() + 1))) {

            ped = ped.getPedido(ped.getId() + 1);
            mostrarPedido();

        }
        if (txtIdPedido.getText().equals("0")) {

            ped = ped.getPedido(ped.getPrimerPedido());
            mostrarPedido();
        }

    }

    public void mostrarPedido() {

        txtEmision.setText(ped.getFechaEmision());
        txtIdPedido.setText(String.valueOf(ped.getId()));
        txtTotal.setText(String.valueOf(ped.getMontoTotal()));
        prov = prov.getProv(ped.getIdprov());
        txtProveedor.setText(String.valueOf(prov.getNombre()));
        txtRuc.setText(prov.getRuc());
        det.setIdPed(ped.getId());
        if (ped.getEstado() > 0) {

            chkActivo.setSelected(true);

        } else {

            chkActivo.setSelected(false);

        }
        lista = FXCollections.observableArrayList(det.consulta());
        tblDetallePedido.setItems(lista);

        if (ped.getId() == 0 || chkActivo.isSelected() == false) {

            btnAnularPedido.setDisable(true);

        } else if (ped.getId() != 0 || chkActivo.isSelected() == true) {

            btnAnularPedido.setDisable(false);

        }

    }

    public void setProductoSeleccionado(producto pro) {
        this.pro = pro;
        if (pro.getId() != -1) {
            txtCodigoProducto.setText(String.valueOf(pro.getId()));
            txtCostoProducto.setText(String.valueOf(pro.getCosto()));
        }
    }

    public void setProveedorSeleccionado(proveedor prov) {
        this.prov = prov;
        if (prov.getId() != -1) {
            txtProveedor.setText(prov.getNombre());
            txtRuc.setText(prov.getRuc());
        }
    }
    
    public void setPedidoSeleccionado(pedido ped){
        this.ped = ped;
        if (ped.getId() != -1) {
            mostrarPedido();
        }
    }

    public void abrirFxmlModal(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Obtener el controlador del destino
            if (titulo.equals("Buscar Proveedor")) {

                controladorDestinoP = loader.getController();

            } else if (titulo.equals("Buscar Producto")) {

                controladorDestinoA = loader.getController();

            } else {

                controladorDestinoPed = loader.getController();

            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false); // No permitir redimensionar

            // Hacer que la ventana sea modal y bloquear la interacción con la ventana principal
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(primaryStage);

            // Pasar el Stage y el controlador de origen al controlador de destino
            if (titulo.equals("Buscar Producto")) {

                controladorDestinoA.setStage(stage);
                controladorDestinoA.setControladorPedidos(this);

            } else if (titulo.equals("Buscar Proveedor")) {

                controladorDestinoP.setStage(stage);
                controladorDestinoP.setControladorPedidos(this);

            } else {

                controladorDestinoPed.setStage(stage);
                controladorDestinoPed.setControladorPedidos(this);

            }

            // Mostrar y esperar a que se cierre
            stage.showAndWait();

            if (pro.getId() == -1) {

                txtCodigoProducto.clear();
                txtCostoProducto.clear();

            } else {

                txtCostoProducto.setDisable(true);

            }

        } catch (IOException ex) {
            Logger.getLogger(VistaPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToSearchProducto(ActionEvent event) {

        abrirFxmlModal("vistaBuscarArticulos.fxml", "Buscar Producto");

    }

    @FXML
    private void goToSearchProveedor(ActionEvent event) {

        abrirFxmlModal("vistaBuscarProveedores.fxml", "Buscar Proveedor");

    }

}
