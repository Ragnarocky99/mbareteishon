package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.clases.reporte;
import com.mycompany.mbareteishon.modelo.cliente;
import com.mycompany.mbareteishon.modelo.detalleFactura;
import com.mycompany.mbareteishon.modelo.empleado;
import com.mycompany.mbareteishon.modelo.factura;
import com.mycompany.mbareteishon.modelo.producto;
import com.mycompany.mbareteishon.modelo.usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VistaVentasController implements Initializable {

    @FXML
    private TextField txtFechaEmision;
    @FXML
    private TextField txtNroFactura;
    @FXML
    private TextField txtEmpleado;
    @FXML
    private TextField txtRucCliente;
    @FXML
    private Button btnBuscarCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private ComboBox<String> cmbTipoVenta;
    @FXML
    private ComboBox<String> cmbFormaPago;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnBuscarFactura;
    @FXML
    private Button btnSiguiente;
    @FXML
    private TableView<detalleFactura> tblDetalleFactura;
    @FXML
    private TableColumn<detalleFactura, Integer> colIdProducto;
    @FXML
    private TableColumn<detalleFactura, String> colNombreProducto;
    @FXML
    private TableColumn<detalleFactura, Double> colPrecioProducto;
    @FXML
    private TableColumn<detalleFactura, Integer> colCantidadProducto;
    @FXML
    private TableColumn<detalleFactura, Double> colMontoTotalProducto;
    @FXML
    private TableColumn<detalleFactura, Double> colGrav10;
    @FXML
    private Button btnModificarProducto;
    @FXML
    private Button btnEliminarProducto;
    @FXML
    private TableView<factura> tblTotales;
    @FXML
    private TableColumn<factura, Double> colTotalGral;
    @FXML
    private TableColumn<factura, Double> colTotal10;
    @FXML
    private Button btnNuevoFactura;
    @FXML
    private Button btnGuardarFactura;
    @FXML
    private Button btnAnularFactura;
    @FXML
    private Button btnCancelarFactura;
    @FXML
    private TextField txtCodProducto;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private TextField txtCantidadProducto;
    @FXML
    private TextField txtPrecioProducto;
    @FXML
    private Button btnAceptarProducto;
    @FXML
    private Button btnCancelarProducto;
    @FXML
    private CheckBox boxActivo;
    @FXML
    private Button btnAgregarProdcucto;
    @FXML
    private Button btnImprimirFactura;

    private Stage primaryStage;

    boolean modificar = false;
    int c = 0;
    int pos;
    double totalGral = 0;
    double totalIva = 0;

    LocalDateTime currentDate = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDate;

    ObservableList<detalleFactura> lista = FXCollections.observableArrayList();

    detalleFactura det = new detalleFactura();
    
    reporte rep = new reporte();

    ObservableList<factura> totales = FXCollections.observableArrayList();

    public factura fac = new factura();

    producto pro = new producto();

    empleado empAdmin = new empleado();

    cliente cl = new cliente();

    private empleado emp = new empleado();
    
    VistamMenuController vM;

    VistaBuscarFacturaController controladorDestinoF;
    VistaBuscarClientesController controladorDestinoC;
    VistaBuscarArticulosController controladorDestinoP;

    public void initialize(URL url, ResourceBundle rb) {

        empAdmin.setPswd(empAdmin.getAdminPswd());
        totales.add(fac);
        initFactura();
        this.primaryStage = primaryStage;
        cmbTipoVenta.getItems().addAll("Mostrador", "Delivery");
        cmbFormaPago.getItems().addAll("Efectivo", "Credito");

        fac.setNumeroFactura(fac.getUltimaFactura());
        //txtEmpleado.setText(emp.getNombre() + " " + emp.getApellido());

        colCantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colGrav10.setCellValueFactory(new PropertyValueFactory<>("iva10"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colMontoTotalProducto.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        colTotal10.setCellValueFactory(new PropertyValueFactory<>("iva10"));
        colTotalGral.setCellValueFactory(new PropertyValueFactory<>("totalGral"));

        mostrarFactura();

    }

    public void setEmpleado(empleado empleado) {
        this.emp = empleado;
        // Actualiza la vista con la información del empleado
        if (txtEmpleado != null && emp != null) {
            txtEmpleado.setText(emp.getNombre() + " " + emp.getApellido());
        }
    }

    public void initFactura() {

        lista = FXCollections.observableArrayList();
        txtFechaEmision.setDisable(true);
        txtNroFactura.setDisable(true);
        txtEmpleado.setDisable(true);
        txtRucCliente.setDisable(true);
        txtNombreCliente.setDisable(true);
        btnBuscarCliente.setDisable(true);
        boxActivo.setDisable(true);
        cmbFormaPago.setDisable(true);
        cmbTipoVenta.setDisable(true);
        //habilitar atras sgte buscar
        btnAtras.setDisable(false);
        btnSiguiente.setDisable(false);
        btnBuscarFactura.setDisable(false);
        //
        tblDetalleFactura.setDisable(true);
        btnAgregarProdcucto.setDisable(true);
        btnModificarProducto.setDisable(true);
        btnEliminarProducto.setDisable(true);
        tblTotales.setDisable(true);
        txtCodProducto.setDisable(true);
        btnBuscarProducto.setDisable(true);
        txtCantidadProducto.setDisable(true);
        txtPrecioProducto.setDisable(true);
        btnAceptarProducto.setDisable(true);
        btnCancelarProducto.setDisable(true);
        btnNuevoFactura.setDisable(false); // 
        btnGuardarFactura.setDisable(true);
        btnAnularFactura.setDisable(true);
        btnCancelarFactura.setDisable(true);
        btnImprimirFactura.setDisable(true);

    }

    public void consFactura() {
        txtFechaEmision.setDisable(true);
        txtNroFactura.setDisable(true);
        txtEmpleado.setDisable(true);
        txtRucCliente.setDisable(true);
        txtNombreCliente.setDisable(true);
        btnBuscarCliente.setDisable(false);
        boxActivo.setDisable(true);
        cmbFormaPago.setDisable(false);
        cmbTipoVenta.setDisable(false);
        btnAtras.setDisable(true);
        btnSiguiente.setDisable(true);
        btnBuscarFactura.setDisable(true);
        tblDetalleFactura.setDisable(false);
        btnAgregarProdcucto.setDisable(false);
        btnModificarProducto.setDisable(true);
        btnEliminarProducto.setDisable(true);
        tblTotales.setDisable(false);
        txtCodProducto.setDisable(true);
        btnBuscarProducto.setDisable(true);
        txtCantidadProducto.setDisable(true);
        txtPrecioProducto.setDisable(true);
        btnAceptarProducto.setDisable(true);
        btnCancelarProducto.setDisable(true);
        btnNuevoFactura.setDisable(true);
        btnGuardarFactura.setDisable(false);
        btnAnularFactura.setDisable(true);
        btnCancelarFactura.setDisable(false);
        btnImprimirFactura.setDisable(true);
    }

    public void aggDetalle() {
        btnAgregarProdcucto.setDisable(true);
        btnModificarProducto.setDisable(true);
        btnEliminarProducto.setDisable(true);
        btnBuscarProducto.setDisable(false);
        btnBuscarProducto.requestFocus();
        txtCantidadProducto.setDisable(false);
        btnAceptarProducto.setDisable(false);
        btnCancelarProducto.setDisable(false);
    }

    public void mostrarFactura() {

        if (fac.getNumeroFactura() != -1) {
            fac = fac.getFacturaFromDb(fac.getNumeroFactura());
            txtFechaEmision.setText(String.valueOf(fac.getFechaEmision()));
            txtNroFactura.setText(String.valueOf(fac.getNumeroFactura()));
            empleado e = new empleado();
            e = e.getUserFromDb(fac.getIdEmpleado());
            txtEmpleado.setText(e.getNombre() + " " + e.getApellido());
            cl = cl.getClienteFromDb(fac.getIdCliente());
            txtRucCliente.setText(cl.getRucCiCliente());
            txtNombreCliente.setText(cl.getNombreCliente() + " " + cl.getApellidoCliente());
            det.setIdProducto(fac.getNumeroFactura());
            lista = FXCollections.observableArrayList(det.consulta());

            for (detalleFactura deta : lista) {
                deta.setNombre(deta.getNombre(deta.getIdProducto()));
            }

            totales = FXCollections.observableArrayList(fac.getFacturaFromDb(fac.getNumeroFactura()));

            tblTotales.setItems(totales);
            tblDetalleFactura.setItems(lista);
            btnAnularFactura.setDisable(false);

            if (fac.getActivo() == 0) {
                boxActivo.setSelected(false);
                btnAnularFactura.setDisable(true);
                btnImprimirFactura.setDisable(true);
            } else {
                boxActivo.setSelected(true);
                btnAnularFactura.setDisable(false);
                btnImprimirFactura.setDisable(false);
            }

            if (fac.getTipoVenta().equals(cmbFormaPago.getItems().get(0))) {
                cmbTipoVenta.setValue("Mostrador");
            } else {
                cmbTipoVenta.setValue("Delivery");
            }

            if (fac.getFormaPago().equals(cmbFormaPago.getItems().get(0))) {
                cmbFormaPago.setValue("Efectivo");
            } else {
                cmbFormaPago.setValue("Credito");
            }
        }

    }

    public void setClienteSeleccionado(cliente cl) {
        this.cl = cl;
        if (cl.getIdCliente() != -1) {
            txtRucCliente.setText(cl.getRucCiCliente());
            txtNombreCliente.setText(cl.getNombreCliente() + " " + cl.getApellidoCliente());

        }
    }

    public void setProductoSeleccionado(producto pro) {
        this.pro = pro;
        if (pro.getId() != -1) {
            txtCodProducto.setText(String.valueOf(pro.getId()));
            txtPrecioProducto.setText(String.valueOf(pro.getPrecio()));
        }
    }

    public void setFacturaSeleccionada(factura fac) {
        this.fac = fac;
        if (fac.getNumeroFactura() != -1) {

            mostrarFactura();

        }
    }

    public void setVM(VistamMenuController vM){
        this.vM = vM;
    }
    
    @FXML
    private void buscarCliente(ActionEvent event) {

        abrirFxmlModal("vistaBuscarClientes.fxml", "Buscar Cliente");

    }

    @FXML
    private void atras(ActionEvent event) {

        factura facVacio = new factura();
        if (fac.getFacturaFromDb(fac.getNumeroFactura() - 1).getNumeroFactura() != 0) {

            fac = fac.getFacturaFromDb(fac.getNumeroFactura() - 1);
            mostrarFactura();

        } else {

            fac = fac.getFacturaFromDb(fac.getUltimaFactura());
            mostrarFactura();
        }

    }

    @FXML
    private void buscarFactura(ActionEvent event) {

        abrirFxmlModal("vistaBuscarFactura.fxml", "Buscar Ventas");

    }

    @FXML
    private void siguiente(ActionEvent event) {

        factura facVacio = new factura();
        if (fac.getFacturaFromDb(fac.getNumeroFactura() + 1).getNumeroFactura() != 0) {

            fac = fac.getFacturaFromDb(fac.getNumeroFactura() + 1);
            mostrarFactura();

        }
        if (txtNroFactura.getText().equals("0")) {

            fac = fac.getFacturaFromDb(fac.getPrimeraFactura());
            mostrarFactura();
        }

    }

    @FXML
    private void noMostrarFila(MouseEvent event) {
    }

    @FXML
    private void mostrarFila(MouseEvent event) {

        det = tblDetalleFactura.getSelectionModel().getSelectedItem();
        btnModificarProducto.setDisable(false);
        btnEliminarProducto.setDisable(false);

    }

    @FXML
    private void agregarProducto(ActionEvent event) {

        tblDetalleFactura.setDisable(true);
        tblTotales.setDisable(true);
        btnBuscarProducto.requestFocus();
        aggDetalle();
        modificar = false;

    }

    @FXML
    private void modificarProducto(ActionEvent event) {

        tblDetalleFactura.setEditable(true);
        tblTotales.setDisable(true);
        aggDetalle();
        btnBuscarProducto.requestFocus();
        txtCantidadProducto.setText(String.valueOf(det.getCantidad()));
        txtCodProducto.setText(String.valueOf(det.getIdProducto()));
        txtPrecioProducto.setText(String.valueOf(det.getMonto()));
        modificar = true;

    }

    @FXML
    private void eliminarProducto(ActionEvent event) {

        det = tblDetalleFactura.getSelectionModel().getSelectedItem();
        totalGral = 0;
        totalIva = 0;

        lista.remove(det);
        c = 1;
        for (detalleFactura det : lista) {
            det.setNro(c);
            c += 1;
        }
        for (detalleFactura det : lista) {
            totalGral += det.getMontoTotal();
            totalIva += det.getIva10();
        }
        fac.setTotalGral(totalGral);
        fac.setIva10(totalIva);
        totales.set(0, fac);
        tblTotales.setItems(totales);
        c = 0;
        consFactura();

    }

    @FXML
    private void nuevo(ActionEvent event) {

        consFactura();
        txtNroFactura.clear();
        txtRucCliente.clear();
        txtNombreCliente.clear();
        cmbFormaPago.getSelectionModel().clearSelection();
        cmbTipoVenta.getSelectionModel().clearSelection();
        lista = FXCollections.observableArrayList();
        tblDetalleFactura.setItems(lista);
        totales = FXCollections.observableArrayList();
        txtFechaEmision.clear();
        tblTotales.setItems(totales);

        txtEmpleado.setText(emp.getNombre() + " " + emp.getApellido());
        fac = new factura();
        fac.setNumeroFactura(fac.getUltimaFactura() + 1);
        txtNroFactura.setText(String.valueOf(fac.getNumeroFactura()));
        currentDate = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");
        formattedDate = currentDate.format(formatter);
        txtFechaEmision.setText(formattedDate);
        btnBuscarCliente.requestFocus();

    }

    @FXML
    private void guardar(ActionEvent event) {

        System.out.println(emp.getNombre());

        if (txtRucCliente.getText().equals("") || txtNombreCliente.getText().equals("")) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("No se ha seleccionado un cliente");
            alerta.showAndWait();

        } else if (cmbTipoVenta.getSelectionModel().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("No se ha seleccionado un tipo de venta");
            alerta.showAndWait();

        } else if (cmbFormaPago.getSelectionModel().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("No se ha seleccionado una forma de pago");
            alerta.showAndWait();

        } else if (lista.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("La tabla esta vacia");
            alerta.showAndWait();

        } else {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("El sistema comunica;");
            alerta.setHeaderText(null);
            alerta.setContentText("¿Desea grabar la factura?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if (opcion.get() == ButtonType.OK) {
                fac = new factura(Timestamp.valueOf(currentDate), Integer.parseInt(txtNroFactura.getText()), cl.getIdCliente(), emp.getIdEmpleado(), totalGral, cmbFormaPago.getValue(), cmbTipoVenta.getValue(), 1, totalIva);
                if (fac.insertar()) {
                    c = 0;
                    for (detalleFactura detalle : lista) {
                        c += 1;
                        det = new detalleFactura(c, detalle.getCantidad(), detalle.getIdProducto(), detalle.getNumeroFactura(), detalle.getMontoTotal(), detalle.getMonto(), detalle.getIva10());
                        det.insertar();
                        pro.setId(det.getIdProducto());
                        pro.eliminarStock(det.getCantidad());

                    }
                    Alert alertaIn = new Alert(Alert.AlertType.INFORMATION);
                    alertaIn.setTitle("El sistema comunica:");
                    alertaIn.setHeaderText(null);
                    alertaIn.setContentText("Insertado correctamente en la base de datos");
                    alertaIn.show();
                    totalGral = 0;
                    modificar = false;
                    c = 0;
                    mostrarFactura();
                    initFactura();
                    
                    vM.updEstadisticas();

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
    private void anular(ActionEvent event) {

        if (emp.getCargo().equals("Admin")) {

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("El sistema comunica;");
            alerta.setHeaderText(null);
            alerta.setContentText("Realmente desea anular este pedido?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if (opcion.get() == ButtonType.OK) {
                fac.setActivo(0);
                System.out.println(fac.modificar());
            }
            mostrarFactura();

        } else {

            // Crear un Alert personalizado
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Autenticación requerida");
            alert.setHeaderText("Ingrese su contraseña de administrador.");

            // Crear un PasswordField para la entrada de contraseña
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Contraseña");

            // Crear un GridPane para organizar el PasswordField en el Alert
            GridPane grid = new GridPane();
            grid.add(passwordField, 0, 0);

            // Configurar el contenido del Alert
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setContent(grid);

            // Añadir los botones OK y Cancel
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Mostrar el Alert y esperar la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();

            // Manejar la respuesta del usuario
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String enteredPassword = passwordField.getText();
                // Comparar la contraseña ingresada
                if (empAdmin.checkPassword(enteredPassword)) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("El sistema comunica;");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Realmente desea anular este pedido?");
                    Optional<ButtonType> opcion = alerta.showAndWait();
                    if (opcion.get() == ButtonType.OK) {
                        fac.setActivo(0);
                        System.out.println(fac.modificar());
                    }
                    mostrarFactura();

                } else {
                    System.out.println("Contraseña incorrecta");
                    Alert alertaIn = new Alert(Alert.AlertType.INFORMATION);
                    alertaIn.setTitle("El sistema comunica:");
                    alertaIn.setHeaderText(null);
                    alertaIn.setContentText("Contraseña incorrecta");
                    alertaIn.show();
                }
            } else {
                System.out.println("Autenticación cancelada");
            }

        }
    }

    @FXML
    private void cancelar(ActionEvent event) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("El sistema comunica;");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Desea cancelar la venta?");
        Optional<ButtonType> opcion = alerta.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            initFactura();
            txtNroFactura.clear();
            txtRucCliente.clear();
            txtNombreCliente.clear();
            cmbFormaPago.getSelectionModel().clearSelection();
            cmbTipoVenta.getSelectionModel().clearSelection();
            lista = FXCollections.observableArrayList();
            tblDetalleFactura.setItems(lista);
            totales = FXCollections.observableArrayList();
            txtFechaEmision.clear();
            tblTotales.setItems(totales);
        }

    }

    @FXML
    private void aceptarProducto(ActionEvent event) {

        try {
            totalGral = 0;
            if (!modificar) {

                if ("".equals(txtCodProducto.getText())) {
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

                    int cant = Integer.parseInt(txtCantidadProducto.getText());
                    double precio = Double.parseDouble(txtPrecioProducto.getText());
                    double total = cant * precio;
                    det = new detalleFactura(lista.size() + 1, cant, Integer.parseInt(txtCodProducto.getText()), fac.getNumeroFactura(), total, precio, total * 0.1);
                    det.setNombre(pro.getNombre());
                    if (comprobarProdRepetido()) {

                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("El sistema comunica;");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Se detecto una repeticion de productos en \n"
                                + "ID : " + det.getIdProducto() + " Nombre : " + pro.getNombre() + "\n"
                                + "Desea combinar sus cantidades?");
                        Optional<ButtonType> opcion = alerta.showAndWait();
                        if (opcion.get() == ButtonType.OK) {

                            c = 0;
                            for (detalleFactura df : lista) {

                                df.setNro(c + 1);
                                c += 1;

                            }

                            lista.set(det.getNro() - 1, det);
                            tblDetalleFactura.setItems(lista);

                        }

                    } else {

                        lista.add(det);
                        tblDetalleFactura.setItems(lista);

                    }

                }

            } else {

                if ("".equals(txtCodProducto.getText())) {
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
                    pro.setId(Integer.parseInt(txtCodProducto.getText()));
                    int cant = Integer.parseInt(txtCantidadProducto.getText());
                    double precio = Double.parseDouble(txtPrecioProducto.getText());
                    double total = cant * precio;

                    //det = new detalleFactura(pos, pro.getId(), cant, cost, total, Integer.parseInt(txtIdPedido.getText()), det.consultaNombre(pro.getId()));
                    det = new detalleFactura(pos, cant, Integer.parseInt(txtCodProducto.getText()), fac.getNumeroFactura(), total, precio, total * 0.1);
                    det.setNombre(pro.getNombre());
                    if (comprobarProdRepetido()) {

                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("El sistema comunica;");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Se detecto una repeticion de productos en " + det.getNro() + "\n"
                                + "ID : " + det.getIdProducto() + " Nombre : " + pro.getNombre() + "\n"
                                + "Desea combinar sus cantidades?");
                        Optional<ButtonType> opcion = alerta.showAndWait();
                        if (opcion.get() == ButtonType.OK) {

                            lista.set(det.getNro() - 1, det);
                            lista.remove(pos - 1);
                            tblDetalleFactura.setItems(lista);

                            c = 0;
                            for (detalleFactura df : lista) {
                                
                                df.setNro(c + 1);
                                c += 1;

                            }

                        }

                    } else {
                        lista.set(det.getNro() - 1, det);
                        tblDetalleFactura.setItems(lista);

                    }

                }

            }
        } catch (Exception e) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("");
            alerta.setHeaderText(null);
            alerta.setContentText("Valores invalidos");
            alerta.showAndWait();

        }

        totalGral = 0;
        totalIva = 0;
        for (detalleFactura det : lista) {
            det.setIva10(det.getMontoTotal()*0.1);
            totalGral += det.getMontoTotal();
            totalIva += det.getIva10();
            System.out.println(det.getIva10());
        }

        fac.setTotalGral(totalGral);
        fac.setIva10(totalIva);
        totales.clear();
        totales.add(fac);
        tblTotales.setItems(totales);

        txtCodProducto.clear();
        txtCantidadProducto.clear();
        txtPrecioProducto.clear();

        consFactura();

    }

    public boolean comprobarProdRepetido() {

        for (detalleFactura detalle : lista) {

            if ((det.getIdProducto() == detalle.getIdProducto()) && (det.getNro() != detalle.getNro())) {

                int cant = det.getCantidad() + detalle.getCantidad();
                double precio = detalle.getMonto();
                double total = cant * precio;

                //det = new detalleFactura(detalle.getNro(), detalle.getIdProducto(), cant, cost, total, detalle.getId(), detalle.getNombre());
                det = new detalleFactura(detalle.getNro(), cant, detalle.getIdProducto(), fac.getNumeroFactura(), total, precio, precio * 0.1);
                det.setNombre(pro.getNombre());
                return true;

            } else {

            }
        }

        return false;

    }

    @FXML
    private void cancelarProducto(ActionEvent event) {

        consFactura();
        txtCodProducto.clear();
        txtCantidadProducto.clear();
        txtPrecioProducto.clear();

    }

    @FXML
    private void buscarProducto(ActionEvent event) {

        abrirFxmlModal("vistaBuscarArticulos.fxml", "Buscar Producto");

    }

    @FXML
    private void imprimirFactura(ActionEvent event) {
        
        System.out.println(fac.getNumeroFactura());
        rep.generarReporteF("/reportes/factura.jasper","Factura", fac.getNumeroFactura());
        
    }

    public void abrirFxmlModal(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Obtener el controlador del destino
            if (titulo.equals("Buscar Cliente")) {

                controladorDestinoC = loader.getController();

            } else if (titulo.equals("Buscar Producto")) {

                controladorDestinoP = loader.getController();

            } else if (titulo.equals("Buscar Ventas")) {

                controladorDestinoF = loader.getController();

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
                controladorDestinoC.setControladorVentas(this);

            } else if (titulo.equals("Buscar Producto")) {

                controladorDestinoP.setStage(stage);
                controladorDestinoP.setControladorVentas(this);

            } else if (titulo.equals("Buscar Ventas")) {

                controladorDestinoF.setPrimaryStage(stage);
                controladorDestinoF.setControladorVentas(this);

            }

            // Mostrar y esperar a que se cierre
            stage.showAndWait();

            if (pro.getId() == -1) {

                txtCodProducto.clear();
                txtCantidadProducto.clear();

            }

        } catch (IOException ex) {
            Logger.getLogger(VistaPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
