/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.ValidadorString;
import com.mycompany.mbareteishon.modelo.cliente;
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
import javafx.scene.control.CheckBox;
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
public class VistaGestionClientesController implements Initializable {


    @FXML
    private TextField txtBuscarCliente;
    @FXML
    private TableView<cliente> tblCliente;
    @FXML
    private TableColumn<cliente, String> colRucCliente;
    @FXML
    private TableColumn<cliente, String> colNombreCliente;
    @FXML
    private TableColumn<cliente, String> colApellidoCliente;
    @FXML
    private Button btnIrA;
    @FXML
    private Button btnAgregarCliente;
    @FXML
    private Button btnModificarCliente;
    @FXML
    private Button btnEliminarCliente;
    @FXML
    private TextField txtIdCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtApellidoCliente;
    @FXML
    private TextField txtRucCliente;
    private CheckBox chkActivo;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    
    cliente one = new cliente();
    ObservableList<cliente> lista;
    ObservableList<cliente> listaFiltrada;
    
    private boolean modificar;
    @FXML
    private Tab paneInfoCliente;
    @FXML
    private TabPane tabPaneClientes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnAgregarCliente.setDisable(false);
        txtNombreCliente.setDisable(true);
        txtApellidoCliente.setDisable(true);
        txtRucCliente.setDisable(true);
        chkActivo.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true);
        btnIrA.setDisable(true);
        
        mostrarDatos();
        
        
        
    }    
    
    @FXML
    private void verInfoCliente(ActionEvent event) {
        
        tabPaneClientes.getSelectionModel().select(paneInfoCliente);
        
    }

    @FXML
    private void agregarCliente(ActionEvent event) {
        
        modificar = false;
        
        btnAgregarCliente.setDisable(true);
        txtNombreCliente.setDisable(false);
        txtApellidoCliente.setDisable(false);
        txtRucCliente.setDisable(false);
        chkActivo.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true);
        
        txtNombreCliente.clear();
        txtIdCliente.clear();
        txtRucCliente.clear();
        txtApellidoCliente.clear();
        
        
        
    }

    @FXML
    private void modificarCliente(ActionEvent event) {
        
        one.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
        
        modificar = true;
        
        btnAgregarCliente.setDisable(true);
        txtNombreCliente.setDisable(false);
        txtApellidoCliente.setDisable(false);
        txtRucCliente.setDisable(false);
        chkActivo.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true);
        
    }

    @FXML
private void eliminarCliente(ActionEvent event) {
    // Obtener el ID del cliente desde el campo correspondiente
    int idCliente = Integer.parseInt(txtIdCliente.getText());

    // Crear una alerta de confirmación
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    alertaConfirmacion.setTitle("Confirmar Eliminación");
    alertaConfirmacion.setHeaderText(null);
    alertaConfirmacion.setContentText("¿Estás seguro de eliminar este cliente? Esta acción no se puede deshacer.");

    // Mostrar la alerta de confirmación y esperar a que el usuario elija una opción
    Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
        // El usuario ha confirmado la eliminación, proceder con la operación
        one.setIdCliente(idCliente);
        
        if (one.eliminar()) {
            // Mostrar mensaje de eliminación exitosa
            Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
            alertaExito.setTitle("Eliminación Exitosa");
            alertaExito.setHeaderText(null);
            alertaExito.setContentText("Cliente eliminado correctamente.");
            alertaExito.showAndWait();
            
            txtNombreCliente.clear();
            txtIdCliente.clear();
            txtRucCliente.clear();
            txtApellidoCliente.clear();
            
        } else {
            // Mostrar mensaje de error
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setHeaderText(null);
            alertaError.setContentText("No se pudo eliminar el cliente.");
            alertaError.showAndWait();
            
            txtNombreCliente.setText(one.getNombreCliente());
            txtIdCliente.setText(String.valueOf(one.getIdCliente()));
            txtRucCliente.setText(one.getRucCiCliente());
            txtApellidoCliente.setText(one.getApellidoCliente());
            
        }

        // Deshabilitar controles y actualizar datos
        btnAgregarCliente.setDisable(false);
        txtNombreCliente.setDisable(true);
        txtApellidoCliente.setDisable(true);
        txtRucCliente.setDisable(true);
        chkActivo.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true);
        btnIrA.setDisable(true);
        
        mostrarDatos();
    }
}
    
    private void mostrarDatos(){
        
        lista = FXCollections.observableArrayList(one.consulta());
        colRucCliente.setCellValueFactory(new PropertyValueFactory<>("rucCiCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));    
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
        tblCliente.setItems(lista);
        
    }

    @FXML
    private void aceptar(ActionEvent event) {
        
        String rucCliente = txtRucCliente.getText();
        System.out.println("RUC Cliente: " + rucCliente);  // Mensaje de depuración

        if(ValidadorString.isValid(rucCliente)){
            one.setRucCiCliente(rucCliente);
            System.out.println("RUC Cliente válido: " + rucCliente);  // Mensaje de depuración
            one.setNombreCliente(txtNombreCliente.getText());
            one.setApellidoCliente(txtApellidoCliente.getText());

            if(modificar){
                if(one.modificar()){
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Modificado correctamente");
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido modificar correctamente");
                    alerta.show();
                    
                    txtNombreCliente.setText(one.getNombreCliente());
                    txtIdCliente.setText(String.valueOf(one.getIdCliente()));
                    txtRucCliente.setText(one.getRucCiCliente());
                    txtApellidoCliente.setText(one.getApellidoCliente());
                    
                }
            } else {
                if(one.insertar()){
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Insertado correctamente");
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido insertar correctamente");
                    alerta.show();
                }
            }
        } else {
            System.out.println("RUC Cliente inválido: " + rucCliente);  // Mensaje de depuración
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("RUC/CI Invalido");
            alerta.show(); 
        }

        btnAgregarCliente.setDisable(false);
        txtNombreCliente.setDisable(true);
        txtApellidoCliente.setDisable(true);
        txtRucCliente.setDisable(true);
        chkActivo.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true);

        mostrarDatos();
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
        btnIrA.setDisable(true);
        
        txtNombreCliente.clear();
        txtIdCliente.clear();
        txtRucCliente.clear();
        txtApellidoCliente.clear();
        
        btnAgregarCliente.setDisable(false);
        txtNombreCliente.setDisable(true);
        txtApellidoCliente.setDisable(true);
        txtRucCliente.setDisable(true);
        chkActivo.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true);    
        
    }

    @FXML
    private void buscar(KeyEvent event) {
        
        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscarCliente.getText();
        if(buscar.isEmpty()){
            
            tblCliente.setItems(lista);
            
        }
        else{
            
            listaFiltrada.clear();
            for (cliente listas : lista) {
                
                if(listas.getNombreCliente().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listas);
                    
                }
                if(listas.getRucCiCliente().contains(buscar)){
                    
                    listaFiltrada.add(listas);
                    
                }
                   
                
            }
            
            tblCliente.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        
        cliente one = tblCliente.getSelectionModel().getSelectedItem();
        btnIrA.setDisable(false);
        
        txtNombreCliente.setText(String.valueOf(one.getNombreCliente()));
        txtIdCliente.setText(String.valueOf(one.getIdCliente()));
        txtRucCliente.setText(String.valueOf(one.getRucCiCliente()));
        txtApellidoCliente.setText(String.valueOf(one.getApellidoCliente()));
        
        txtNombreCliente.setDisable(true);
        txtApellidoCliente.setDisable(true);
        txtRucCliente.setDisable(true);
        chkActivo.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarCliente.setDisable(false);
        btnEliminarCliente.setDisable(false); 
        btnAgregarCliente.setDisable(false);
        
    }

    @FXML
    private void noMostrarFila(MouseEvent event) {
        
        btnIrA.setDisable(true);
        
        txtNombreCliente.clear();
        txtIdCliente.clear();
        txtRucCliente.clear();
        txtApellidoCliente.clear();
        
        txtNombreCliente.setDisable(true);
        txtApellidoCliente.setDisable(true);
        txtRucCliente.setDisable(true);
        chkActivo.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarCliente.setDisable(true);
        btnEliminarCliente.setDisable(true); 
        
    }
    

}
