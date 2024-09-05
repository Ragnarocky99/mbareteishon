package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.usuario;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaGestionUsuariosController implements Initializable {

    @FXML
    private TabPane tabPaneUsuarios;
    @FXML
    private TextField txtBuscarUsuario;
    @FXML
    private TableView<usuario> tblUsuario;
    private TableColumn<usuario, Integer> colIdUsuario;
    private TableColumn<usuario, String> colNombreUsuario;
    private TableColumn<usuario, String> colRolUsuario;
    @FXML
    private Button btnIrA;
    @FXML
    private Button btnAgregarUsuario;
    @FXML
    private Button btnModificarUsuario;
    @FXML
    private Button btnEliminarUsuario;
    private TextField txtIdUsuario;
    private TextField txtNombreUsuario;
    private TextField txtRolUsuario;
    private TextField txtPswdUsuario;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    usuario user = new usuario();
    ObservableList<usuario> lista;
    ObservableList<usuario> listaFiltrada;

    private boolean modificar;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colApellido;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPswd;
    @FXML
    private TextField txtApellido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarDatos();

        txtNombreUsuario.setDisable(true);
        txtRolUsuario.setDisable(true);
        txtPswdUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);
    }

    @FXML
    private void buscarUsuario(KeyEvent event) {
        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscarUsuario.getText();
        if (buscar.isEmpty()) {
            tblUsuario.setItems(lista);
        } else {
            listaFiltrada.clear();
            for (usuario listas : lista) {
                if (listas.getNombre().toLowerCase().contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
                if (listas.getRol().contains(buscar)) {
                    listaFiltrada.add(listas);
                }
            }
            tblUsuario.setItems(listaFiltrada);
        }
    }

    @FXML
    private void verInfoUsuario(ActionEvent event) {
        usuario user = tblUsuario.getSelectionModel().getSelectedItem();
        if (user != null) {
            txtIdUsuario.setText(String.valueOf(user.getIdUser()));
            txtNombreUsuario.setText(user.getNombre());
            txtRolUsuario.setText(user.getRol());
            txtPswdUsuario.setText(""); // Limpiar el campo de contraseña

            txtNombreUsuario.setDisable(true);
            txtRolUsuario.setDisable(true);
            txtPswdUsuario.setDisable(true);
            btnAceptar.setDisable(true);
            btnCancelar.setDisable(true);
            btnModificarUsuario.setDisable(false);
            btnEliminarUsuario.setDisable(false);
            btnAgregarUsuario.setDisable(false);
        }
    }

    @FXML
    private void agregarUsuario(ActionEvent event) {
        txtNombreUsuario.clear();
        txtIdUsuario.clear();
        txtRolUsuario.clear();
        txtPswdUsuario.clear();

        modificar = false;

        txtNombreUsuario.setDisable(false);
        txtRolUsuario.setDisable(false);
        txtPswdUsuario.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(true);
    }

    @FXML
    private void modificarUsuario(ActionEvent event) {
        user.setIdUser(Integer.parseInt(txtIdUsuario.getText()));

        modificar = true;

        txtNombreUsuario.setDisable(false);
        txtRolUsuario.setDisable(false);
        txtPswdUsuario.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(true);
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        // Obtener el ID del usuario desde el campo correspondiente
        int idUser = Integer.parseInt(txtIdUsuario.getText());

        // Crear una alerta de confirmación
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmar Eliminación");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿Estás seguro de eliminar este usuario? Esta acción no se puede deshacer.");

        // Mostrar la alerta de confirmación y esperar a que el usuario elija una opción
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // El usuario ha confirmado la eliminación, proceder con la operación
            user.setIdUser(idUser);

            if (user.eliminar()) {
                // Mostrar mensaje de eliminación exitosa
                Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
                alertaExito.setTitle("Eliminación Exitosa");
                alertaExito.setHeaderText(null);
                alertaExito.setContentText("Usuario eliminado correctamente.");
                alertaExito.showAndWait();

                txtNombreUsuario.clear();
                txtIdUsuario.clear();
                txtRolUsuario.clear();
                txtPswdUsuario.clear();
            } else {
                // Mostrar mensaje de error
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setHeaderText(null);
                alertaError.setContentText("No se pudo eliminar el usuario.");
                alertaError.showAndWait();

                txtNombreUsuario.setText(user.getNombre());
                txtIdUsuario.setText(String.valueOf(user.getIdUser()));
                txtRolUsuario.setText(user.getRol());
                txtPswdUsuario.setText(""); // Limpiar el campo de contraseña
            }
        }

        txtIdUsuario.clear();
        txtNombreUsuario.clear();
        txtRolUsuario.clear();
        txtPswdUsuario.clear();

        txtNombreUsuario.setDisable(true);
        txtRolUsuario.setDisable(true);
        txtPswdUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);

        mostrarDatos();
    }

    @FXML
    private void aceptar(ActionEvent event) {
        String nombre = txtNombreUsuario.getText();
        String rol = txtRolUsuario.getText();
        String pswd = txtPswdUsuario.getText();
        user.setNombre(nombre);
        user.setPswd(pswd);
        user.setRol(rol);

            if (modificar) {
                if (user.modificar()) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Modificado correctamente");
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido modificar correctamente");
                    alerta.show();

                    txtIdUsuario.setText(String.valueOf(user.getIdUser()));
                    txtNombreUsuario.setText(user.getNombre());
                    txtRolUsuario.setText(user.getRol());
                    txtPswdUsuario.setText(""); // Limpiar el campo de contraseña
                }
            } else {
                if (user.insertar()) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Insertado correctamente");
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido insertar correctamente");
                    alerta.show();

                    txtIdUsuario.setText(String.valueOf(user.getIdUser()));
                    txtNombreUsuario.setText(user.getNombre());
                    txtRolUsuario.setText(user.getRol());
                    txtPswdUsuario.setText(""); // Limpiar el campo de contraseña
                }
            }
        

        txtNombreUsuario.setDisable(true);
        txtRolUsuario.setDisable(true);
        txtPswdUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);

        mostrarDatos();
    }
    
    @FXML
    private void noMostrarFila(MouseEvent event) {
        btnIrA.setDisable(true);

        txtNombreUsuario.setDisable(true);
        txtRolUsuario.setDisable(true);
        txtPswdUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);
    }
    
    @FXML
    private void mostrarFila(MouseEvent event) {
        usuario user = tblUsuario.getSelectionModel().getSelectedItem();
        btnIrA.setDisable(false);

        txtIdUsuario.setText(String.valueOf(user.getIdUser()));
        txtNombreUsuario.setText(user.getNombre());
        txtRolUsuario.setText(user.getRol());
        txtPswdUsuario.setText(""); // Limpiar el campo de contraseña

        txtNombreUsuario.setDisable(true);
        txtRolUsuario.setDisable(true);
        txtPswdUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(false);
        btnEliminarUsuario.setDisable(false);
        btnAgregarUsuario.setDisable(false);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtIdUsuario.clear();
        txtNombreUsuario.clear();
        txtRolUsuario.clear();
        txtPswdUsuario.clear();

        txtNombreUsuario.setDisable(true);
        txtRolUsuario.setDisable(true);
        txtPswdUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);
    }

    public void mostrarDatos() {
        lista = FXCollections.observableArrayList(user.consulta());
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRolUsuario.setCellValueFactory(new PropertyValueFactory<>("rol"));
        tblUsuario.setItems(lista);
    }
}
