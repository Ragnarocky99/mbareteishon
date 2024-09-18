package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.clases.reporte;
import com.mycompany.mbareteishon.modelo.empleado;
import com.mycompany.mbareteishon.modelo.estadistica;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VistamMenuController implements Initializable {

    @FXML
    private Label txtFecha;
    @FXML
    private Label txtHora;
    @FXML
    private MenuItem menuItemGestionarClientes;
    @FXML
    public Label txtEmp;

    public empleado emp;

    Stage stage;
    
    estadistica est = new estadistica();
    
    reporte rep = new reporte();
    @FXML
    private Label txtCantVen;
    @FXML
    private Label txtVentR;
    @FXML
    private TableView<estadistica> tblArt;
    
    ObservableList<estadistica> estats;
    @FXML
    private TableColumn<estadistica, String> colNom;
    @FXML
    private TableColumn<estadistica, Integer> colStck;
    @FXML
    private TableColumn<estadistica, Integer> colSal;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la fecha y la hora en la interfaz
        inicializarFechaHora();
        updEstadisticas();
        
    }

    public void updEstadisticas(){
        
        est = est.getStats();
        
        estats = FXCollections.observableArrayList(est.getProductosVendidos());
        
        colNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSal.setCellValueFactory(new PropertyValueFactory<>("cant"));
        colStck.setCellValueFactory(new PropertyValueFactory<>("stck"));
        
        tblArt.setItems(estats);
        txtCantVen.setText(est.getCantVendida() + " Gs");
        txtVentR.setText(String.valueOf(est.getVentR()));
        
    }
    
    private void inicializarFechaHora() {
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        txtFecha.setText(formattedDate);

        // Actualizar la hora cada segundo
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            String formattedTime = currentTime.format(timeFormatter);
            txtHora.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    public void setEmpleado(empleado empleado) {
        this.emp = empleado;
        // Mostrar información del empleado en la interfaz
        if (txtEmp != null && emp != null) {
            txtEmp.setText(emp.getNombre() + " " + emp.getApellido());
        }
    }

    @FXML
    private void goToGestionarCliente(ActionEvent event) {
        abrirFxml("vistaGestionClientes.fxml", "Gestión de Clientes");
    }

    @FXML
    private void goToGestionarArticulo(ActionEvent event) {
        abrirFxml("vistaGestionArticulos.fxml", "Gestión de Artículos");
    }

    @FXML
    private void goToGestionarProveedor(ActionEvent event) {
        abrirFxml("vistaGestionProveedor.fxml", "Gestión de Proveedores");
    }

    @FXML
    private void goToAcercaDe(ActionEvent event) {
        abrirFxml("vistaAcercaDe.fxml", "Acerca De");
    }

    @FXML
    private void goToPedidos(ActionEvent event) {
        abrirFxml("vistaPedidos.fxml", "Pedidos");
    }

    @FXML
    private void goToVentas(ActionEvent event) {
        abrirFxml("vistaVentas.fxml", "Ventas");
    }

    private void abrirFxml(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            if ("vistaVentas.fxml".equals(fxml)) {
                VistaVentasController ventasController = loader.getController();
                ventasController.setEmpleado(emp);
                ventasController.setVM(this);
                // Pasar el empleado al controlador
            }
            if ("vistaPedidos.fxml".equals(fxml)) {
                VistaPedidosController pedidosControlller = loader.getController();
                pedidosControlller.setEmp(emp);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false); // No permitir redimensionar
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(VistamMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToInventario(ActionEvent event) {

    }

    @FXML
    private void goToEmpleados(ActionEvent event) {

        if (emp.getCargo().equals("Admin")) {

            abrirFxml("VistaGestionEmpleados.fxml", "Gestion de Empleados");

        } else {

            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setHeaderText(null);
            alertaError.setContentText("Solo los administradores pueden gestionar empleados.");
            alertaError.showAndWait();

        }

    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        emp = new empleado();
        abrirFxml("vistaLogin.fxml", "");
        this.stage.close();

    }

    @FXML
    private void goToNominaClientes(ActionEvent event) {
        rep.generarReporte("/reportes/nomina_clientes.jasper", "Nomina Clientes");
    }
}
