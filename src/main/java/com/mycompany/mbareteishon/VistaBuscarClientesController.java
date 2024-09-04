/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.cliente;
import com.mycompany.mbareteishon.modelo.producto;
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
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author nahue
 */
public class VistaBuscarClientesController implements Initializable {


    @FXML
    private TableView<cliente> tblClientes;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    
    private Stage stage;
    private VistaVentasController controladorVentas;
    private VistaBuscarFacturaController controladorBuscarFactura;

    ObservableList<cliente> lista;
    ObservableList<cliente> listaFiltrada;
    @FXML
    private TableColumn<cliente, String> colRuc;
    @FXML
    private TableColumn<cliente, String> colNombre;
    @FXML
    private TableColumn<cliente, String> colApellido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtBuscar.requestFocus();
        btnAceptar.setDisable(false);
        
    }    
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setControladorBuscarFactura(VistaBuscarFacturaController controladorBuscarFactura) {
        this.controladorBuscarFactura = controladorBuscarFactura;
    }
    

    public void setControladorVentas(VistaVentasController controladorVentas) {
        this.controladorVentas = controladorVentas;
    }
    
    @FXML
    private void mostrarFila(DragEvent event) {
        cliente cl = tblClientes.getSelectionModel().getSelectedItem();
        btnAceptar.setDisable(false);
    }

    @FXML
    private void noMostrarFila(DragEvent event) {
        
        btnAceptar.setDisable(false);
    }

    @FXML
    private void aceptar(ActionEvent event) {
        
        cliente cl = tblClientes.getSelectionModel().getSelectedItem();
        if (cl != null && controladorVentas != null) {
            controladorVentas.setClienteSeleccionado(cl);
        }
        if (cl != null && controladorBuscarFactura != null ){
            controladorBuscarFactura.setClienteSeleccionado(cl);
            controladorBuscarFactura.mostrarCliente();
        }
        stage.close();
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cliente cl = new cliente();
        cl.setIdCliente(-1);
        cl.setNombreCliente("");
        cl.setRucCiCliente("");
        controladorVentas.setClienteSeleccionado(cl);
        
        stage.close();
    }

    @FXML
    private void buscar(ActionEvent event) {
        
        lista = FXCollections.observableArrayList(new cliente().consulta());
        colRuc.setCellValueFactory(new PropertyValueFactory<>("rucCiCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
        tblClientes.setItems(lista);

        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tblClientes.setItems(lista);
        } else {
            listaFiltrada.clear();
            for (cliente listas : lista) {
                if (listas.getNombreCliente().toLowerCase().contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
                if (String.valueOf(listas.getRucCiCliente()).contains(buscar)) {
                    listaFiltrada.add(listas);
                }
                if (String.valueOf(listas.getApellidoCliente()).contains(buscar.toLowerCase())){
                    listaFiltrada.add(listas);
                }
            }
            tblClientes.setItems(listaFiltrada);
        }
        
    }

}
