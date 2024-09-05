/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.empleado;
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
public class VistaGestionEmpleadosController implements Initializable {

    @FXML
    private TabPane tabPaneUsuarios;
    @FXML
    private TextField txtBuscarUsuario;
    @FXML
    private TableView<empleado> tblUsuario;
    @FXML
    private TableColumn<empleado, Integer> colId;
    @FXML
    private TableColumn<empleado, String> colNombre;
    @FXML
    private TableColumn<empleado, String> colApellido;
    @FXML
    private Button btnIrA;
    @FXML
    private Button btnAgregarUsuario;
    @FXML
    private Button btnModificarUsuario;
    @FXML
    private Button btnEliminarUsuario;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPswd;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtApellido;

    boolean modificar;

    empleado emp = new empleado();
    ObservableList<empleado> lista;
    ObservableList<empleado> listaFiltrada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mostrarDatos();

        btnAgregarUsuario.setDisable(false);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);

        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtPswd.setDisable(true);

    }

    public void mostrarDatos() {

        lista = FXCollections.observableArrayList(emp.consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tblUsuario.setItems(lista);

    }

    @FXML
    private void buscarUsuario(KeyEvent event) {

        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscarUsuario.getText();
        if (buscar.isEmpty()) {

            tblUsuario.setItems(lista);

        } else {

            listaFiltrada.clear();
            for (empleado listas : lista) {

                if (listas.getNombre().toLowerCase().contains(buscar.toLowerCase())) {

                    listaFiltrada.add(listas);

                }
                if (listas.getApellido().contains(buscar)) {

                    listaFiltrada.add(listas);

                }

            }

            tblUsuario.setItems(listaFiltrada);

        }

    }

    @FXML
    private void noMostrarFila(MouseEvent event) {
    }

    @FXML
    private void mostrarFila(MouseEvent event) {

        emp = tblUsuario.getSelectionModel().getSelectedItem();
        btnIrA.setDisable(false);

        txtId.setText(String.valueOf(emp.getIdEmpleado()));
        txtNombre.setText(String.valueOf(emp.getNombre()));
        txtApellido.setText(String.valueOf(emp.getApellido()));

        btnAgregarUsuario.setDisable(false);
        btnModificarUsuario.setDisable(false);
        btnEliminarUsuario.setDisable(false);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);

        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtPswd.setDisable(true);

    }

    @FXML
    private void verInfoUsuario(ActionEvent event) {
    }

    @FXML
    private void agregarUsuario(ActionEvent event) {

        modificar = false;

        btnAgregarUsuario.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);

        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtPswd.setDisable(false);

        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtPswd.clear();

    }

    @FXML
    private void modificarUsuario(ActionEvent event) {

        emp.setIdEmpleado(Integer.parseInt(txtId.getText()));

        modificar = true;

        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);

        btnAgregarUsuario.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {

        // Obtener el ID del cliente desde el campo correspondiente
        int idEmp = Integer.parseInt(txtId.getText());

        // Crear una alerta de confirmación
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmar Eliminación");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿Estás seguro de eliminar este usuario? Esta acción no se puede deshacer.");

        // Mostrar la alerta de confirmación y esperar a que el usuario elija una opción
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // El usuario ha confirmado la eliminación, proceder con la operación
            emp.setIdEmpleado(idEmp);

            if (emp.eliminar()) {
                // Mostrar mensaje de eliminación exitosa
                Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
                alertaExito.setTitle("Eliminación Exitosa");
                alertaExito.setHeaderText(null);
                alertaExito.setContentText("Proveedor eliminado correctamente.");
                alertaExito.showAndWait();

                txtId.clear();
                txtNombre.clear();
                txtApellido.clear();
                txtPswd.clear();

            } else {
                // Mostrar mensaje de error
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setHeaderText(null);
                alertaError.setContentText("No se pudo eliminar el proveedor.");
                alertaError.showAndWait();

                txtId.setText(String.valueOf(emp.getIdEmpleado()));
                txtNombre.setText(emp.getNombre());
                txtApellido.setText(emp.getApellido());
                txtPswd.clear();

            }

        }

        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtPswd.clear();

        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);

        mostrarDatos();

    }

    @FXML
    private void aceptar(ActionEvent event) {

        emp.setNombre(txtNombre.getText());
        emp.setApellido(txtApellido.getText());
        emp.setCargo("Ventas");
        emp.setPswd(txtPswd.getText());
        if (modificar) {
            if (emp.modificar()) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setHeaderText(null);
                alerta.setContentText("Modificado correctamente");
                alerta.show();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha podido modificar correctamente");
                alerta.show();

                txtId.setText(String.valueOf(emp.getIdEmpleado()));
                txtNombre.setText(emp.getNombre());
                txtApellido.setText(emp.getApellido());
                txtPswd.clear();

            }
        } else {
            if (emp.insertar()) {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setHeaderText(null);
                alerta.setContentText("Insertado correctamente");
                alerta.show();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setContentText("No se ha podido insertar correctamente");
                alerta.show();

                txtId.setText(String.valueOf(emp.getIdEmpleado()));
                txtNombre.setText(emp.getNombre());
                txtApellido.setText(emp.getApellido());
                txtPswd.clear();

            }
        }

        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);

        mostrarDatos();

    }

    @FXML
    private void cancelar(ActionEvent event) {
        
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtPswd.clear();
        
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarUsuario.setDisable(true);
        btnEliminarUsuario.setDisable(true);
        btnAgregarUsuario.setDisable(false);
        
    }

}