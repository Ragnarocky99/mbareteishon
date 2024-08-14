/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.VistaPedidosController;
import com.mycompany.mbareteishon.modelo.proveedor;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaBuscarProveedoresController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<proveedor> tblProveedores;
    @FXML
    private TableColumn<proveedor, String> colRuc;
    @FXML
    private TableColumn<proveedor, String> colNombre;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnBuscar;
    
    private Stage stage;
    private VistaPedidosController controladorPedidos;

    ObservableList<proveedor> lista;
    ObservableList<proveedor> listaFiltrada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtBuscar.requestFocus();
        btnAceptar.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setControladorPedidos(VistaPedidosController controladorPedidos) {
        this.controladorPedidos = controladorPedidos;
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        proveedor pro = tblProveedores.getSelectionModel().getSelectedItem();
        btnAceptar.setDisable(false);
    }

    @FXML
    private void buscar(ActionEvent event) {
        lista = FXCollections.observableArrayList(new proveedor().consulta());
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        tblProveedores.setItems(lista);

        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tblProveedores.setItems(lista);
        } else {
            listaFiltrada.clear();
            for (proveedor listas : lista) {
                if (listas.getRuc().toLowerCase().contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
                if (listas.getNombre().contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
            }
            tblProveedores.setItems(listaFiltrada);
        }
    }

    @FXML
    private void aceptar(ActionEvent event) {
        proveedor pro = tblProveedores.getSelectionModel().getSelectedItem();
        if (pro != null && controladorPedidos != null) {
            controladorPedidos.setProveedorSeleccionado(pro);
            
        }
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        proveedor pro = new proveedor();
        pro.setNombre("");
        pro.setRuc("");
        pro.setId(-1);
        if (pro != null && controladorPedidos != null) {
            controladorPedidos.setProveedorSeleccionado(pro);
        }
        stage.close();
    }

    @FXML
    private void noMostrarFila(MouseEvent event) {
        
        btnAceptar.setDisable(false);
        
    }

    
}
