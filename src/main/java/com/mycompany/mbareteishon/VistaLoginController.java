/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.usuario;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private String usua;
    private String pswd;

    ArrayList<String> usrs = new ArrayList();
    ObservableList<usuario> lista;

    usuario usr = new usuario();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lista = FXCollections.observableArrayList(usr.consulta());
        for (usuario string : lista) {

            comboUsuario.getItems().addAll(string.getNombre());

        }

    }

    @FXML
    private void actionIngresar(ActionEvent event) {
        usua = comboUsuario.getValue();
        pswd = txtPswd.getText();

        for (usuario nm : lista) {
            if (nm.getNombre().equals(usua) && nm.getPswd().equals(pswd)) {
                String fxml = "";
                String titulo = "";
                switch (nm.getRol()) {
                    case "root":
                        fxml = "VistamMenu.fxml";
                        titulo = "Admin. Mbarete";
                        break;
                    case "ventas":
                        fxml = "VistamMenu.fxml";
                        titulo = "Vendedor. Mbarete";
                        break;
                    case "query":
                        fxml = "VistamMenu.fxml";
                        titulo = "Consultas. Mbarete";
                        break;
                }
                if (!fxml.isEmpty()) {
                    abrirFxml(fxml, titulo, (Stage) ((Node) event.getSource()).getScene().getWindow());
                } else {
                    // Handle case where role is not matched
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Rol no reconocido.");
                    alert.showAndWait();
                }
                return; // Exit after handling the login
            }
        }

        // Handle case where username or password is incorrect
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Nombre de usuario o contraseña incorrectos.");
        alert.showAndWait();
    }

    public void abrirFxml(String fxml, String titulo, Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setMaximized(true); // Maximizar la ventana
            stage.setResizable(false); // No permitir redimensionar
            stage.show();

            // Cerrar la ventana actual
            if (currentStage != null) {
                currentStage.close();
            }

        } catch (IOException ex) {
            System.out.println("Error al abrir el FXML");
            Logger.getLogger(VistamMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
