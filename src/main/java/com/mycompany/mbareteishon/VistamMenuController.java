package com.mycompany.mbareteishon;

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
/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistamMenuController implements Initializable {


    @FXML
    private Label txtFecha;
    @FXML
    private Label txtHora;
    @FXML
    private MenuItem menuItemGestionarClientes;

    public void initialize(URL url, ResourceBundle rb) {
        
        
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();
        // Formatear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = currentDate.format(formatter);
        // Mostrar la fecha en el label
        txtFecha.setText(formattedDate);
        
        // Actualizar la hora cada segundo
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            String formattedTime = currentTime.format(timeFormatter);
            txtHora.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
             
    }   

    @FXML
    private void goToGestionarCliente(ActionEvent event) {
        
        abrirFxml("vistaGestionClientes.fxml","Gestion de Clientes");
        
    }   
    @FXML
    private void goToGestionarArticulo(ActionEvent event) {
        
        abrirFxml("vistaGestionArticulos.fxml","Gestion de Articulos");
        
    }

    @FXML
    private void goToGestionarProveedor(ActionEvent event) {
        
        abrirFxml("vistaGestionProveedor.fxml","Gestion de Proveedores");
        
    }
    
    
    public void abrirFxml(String fxml, String titulo){
        
        // Bloque para crear una nueva escena
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
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
    private void goToAcercaDe(ActionEvent event) {
        
    }

    @FXML
    private void goToPedidos(ActionEvent event) {
        
        abrirFxml("vistaPedidos.fxml", "Pedidos");
        
    }
    
    
}
