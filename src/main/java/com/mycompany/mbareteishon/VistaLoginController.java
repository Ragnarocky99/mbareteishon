/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaLoginController implements Initializable {


    @FXML
    private ComboBox<String> comboUsuario;
    @FXML
    private Button btnIngresar;
    @FXML
    private PasswordField txtPswd;
    private String pswd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        comboUsuario.getItems().addAll("Administrador", "Vendedor", "Contador");
        
        comboUsuario.setOnAction(event -> handleComboBoxSelection(event));
        
    }
    
    // Método para manejar la selección del ComboBox
    private void handleComboBoxSelection(ActionEvent event) {
        String selectedUser = comboUsuario.getSelectionModel().getSelectedItem();
        
        // Realizar diferentes acciones dependiendo de la opción seleccionada
        switch (selectedUser) {
            case "Administrador":
                System.out.println("Se seleccionó Administrador");
                break;
            case "Vendedor":
                System.out.println("Se seleccionó Vendedor");
                break;
            case "Contador":
                System.out.println("Se seleccionó Contador");
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    @FXML
    private void actionIngresar(ActionEvent event) {
        
        
        
    }
    
    public void abrirFxml(String fxml, String titulo){
        
        // Bloque para crear una nueva escena
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setMaximized(true); // Maximizar la ventana
            stage.setResizable(false); // No permitir redimensionar
            stage.show();
            
            
        } catch (IOException ex) {
            System.out.println("ojo");
            Logger.getLogger(VistamMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
