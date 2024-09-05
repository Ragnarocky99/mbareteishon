/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.empleado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaBuscarEmpleadosController implements Initializable {

    @FXML
    private TableView<empleado> tblClientes;
    @FXML
    private TableColumn<empleado, Integer> colId;
    @FXML
    private TableColumn<empleado, String> colNombre;
    @FXML
    private TableColumn<empleado, String> colApe;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private Stage stage;
    private VistaBuscarFacturaController controladorBuscarFactura;

    ObservableList<empleado> lista;
    ObservableList<empleado> listaFiltrada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setControladorBuscarFactura(VistaBuscarFacturaController controladorBuscarFactura) {
        this.controladorBuscarFactura = controladorBuscarFactura;
    }

    @FXML
    private void mostrarFila(DragEvent event) {
        empleado emp = tblClientes.getSelectionModel().getSelectedItem();
        btnAceptar.setDisable(false);
    }

    @FXML
    private void noMostrarFila(DragEvent event) {
        btnAceptar.setDisable(false);
    }

    @FXML
    private void buscar(ActionEvent event) {
        lista = FXCollections.observableArrayList(new empleado().consulta());
        colId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApe.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tblClientes.setItems(lista);

        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tblClientes.setItems(lista);
        } else {
            listaFiltrada.clear();
            for (empleado listas : lista) {
                if (listas.getNombre().toLowerCase().contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
                if (String.valueOf(listas.getIdEmpleado()).contains(buscar)) {
                    listaFiltrada.add(listas);
                }
                if (String.valueOf(listas.getApellido()).contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
            }
            tblClientes.setItems(listaFiltrada);
        }

    }

    @FXML
    private void aceptar(ActionEvent event) {
        empleado emp = tblClientes.getSelectionModel().getSelectedItem();
        if (emp != null && controladorBuscarFactura != null) {
            controladorBuscarFactura.setEmpleadoSeleccionado(emp);
            controladorBuscarFactura.mostrarEmpleado();
        }
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {

        empleado emp = new empleado();
        emp.setIdEmpleado(-1);
        emp.setNombre("");
        emp.setApellido("");
        controladorBuscarFactura.setEmpleadoSeleccionado(emp);

        stage.close();
    }

}
