package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.empleado;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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

    private empleado emp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la fecha y la hora en la interfaz
        inicializarFechaHora();
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
                ventasController.setEmpleado(emp); // Pasar el empleado al controlador
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
}
