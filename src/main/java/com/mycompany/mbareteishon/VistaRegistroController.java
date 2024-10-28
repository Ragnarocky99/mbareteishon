/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.empleado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaRegistroController implements Initializable {

    @FXML
    private PasswordField txtPswd;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private Button btnIngresar;
    @FXML
    private PasswordField txtPswdRep;

    empleado emp = new empleado();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionIngresar(ActionEvent event) {

        if (txtNombre.getText().equals("") || txtApellido.getText().equals("") || txtPswd.getText().equals("") || txtPswdRep.getText().equals("")) {
            showAlert("Advertencia", "Debe rellenar todos los campos");
        } else if (!(txtPswd.getText().equals(txtPswdRep.getText()))) {
            showAlert("", "Las contraseñas no son iguales");
        } else {

            emp = new empleado();
            emp.setNombre(txtNombre.getText());
            emp.setApellido(txtApellido.getText());
            emp.setPswd(txtPswd.getText());
            emp.setEstado(1);
            if (emp.insertar()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("El sistema comunica:");
                alert.setHeaderText(null);
                alert.setContentText("Admin. registrado correctamente");
                alert.showAndWait();
            } else {
                showAlert("El sistema comunica:", "Error, no se pudo registrar");
            }

        }

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
