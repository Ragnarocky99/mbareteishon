package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.empleado;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

public class VistaLoginController implements Initializable {

    @FXML
    private ComboBox<String> comboUsuario;
    @FXML
    private Button btnIngresar;
    @FXML
    private PasswordField txtPswd;

    private ObservableList<empleado> lista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la lista de empleados y cargar en el ComboBox
        lista = FXCollections.observableArrayList(new empleado().consulta());
        for (empleado e : lista) {
            comboUsuario.getItems().add(e.getNombre() + " " + e.getApellido());
        }
    }

    @FXML
    private void actionIngresar(ActionEvent event) {
        String usua = comboUsuario.getValue();
        String pswd = txtPswd.getText(); // Contraseña ingresada por el usuario

        if (usua == null || pswd == null || usua.isEmpty() || pswd.isEmpty()) {
            showAlert("Error", "Por favor, complete todos los campos.");
            return;
        }

        empleado empleadoAutenticado = null;
        for (empleado e : lista) {
            if ((e.getNombre() + " " + e.getApellido()).equals(usua) && e.checkPassword(pswd)) {
                empleadoAutenticado = e;
                break;
            }
        }

        if (empleadoAutenticado == null) {
            showAlert("Error", "Nombre de usuario o contraseña incorrectos.");
        } else {
            String fxml = "";
            String titulo = "";
            switch (empleadoAutenticado.getCargo()) {
                case "Root":
                    fxml = "VistamMenu.fxml";
                    titulo = "Admin. Mbarete";
                    break;
                case "Ventas":
                    fxml = "VistamMenu.fxml";
                    titulo = "Vendedor. Mbarete";
                    break;
                default:
                    showAlert("Error", "Rol no reconocido.");
                    return;
            }
            abrirFxml(fxml, titulo, (Stage) ((Node) event.getSource()).getScene().getWindow(), empleadoAutenticado);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void abrirFxml(String fxml, String titulo, Stage currentStage, empleado empleado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            VistamMenuController vmc = loader.getController();
            vmc.setEmpleado(empleado);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setMaximized(true); // Maximizar la ventana
            stage.setResizable(false); // No permitir redimensionar
            stage.show();

            if (currentStage != null) {
                currentStage.close();
            }

        } catch (IOException ex) {
            System.out.println("Error al abrir el FXML");
            Logger.getLogger(VistaLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
