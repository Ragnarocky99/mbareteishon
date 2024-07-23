/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.proveedor;
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
public class VistaGestionProveedorController implements Initializable {


    @FXML
    private TabPane tabPanePro;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<proveedor> tblProveedor;
    @FXML
    private TableColumn<proveedor, String> colRuc;
    @FXML
    private TableColumn<proveedor, String> colNombre;
    @FXML
    private Button btnIrA;
    @FXML
    private Tab paneInfo;
    @FXML
    private Button btnAgregarProveedor;
    @FXML
    private Button btnModificarProveedor;
    @FXML
    private Button btnEliminarProveedor;
    @FXML
    private TextField txtNombreProveedor;
    @FXML
    private TextField txtRucProveedor;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    
    proveedor prov = new proveedor();
    ObservableList<proveedor> lista;
    ObservableList<proveedor> listaFiltrada;
    
    private boolean modificar;
    @FXML
    private TextField txtIdProveedor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mostrarDatos();
        
        txtNombreProveedor.setDisable(true);
        txtRucProveedor.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarProveedor.setDisable(true);
        btnEliminarProveedor.setDisable(true);
        btnAgregarProveedor.setDisable(false);
        
    }    
    
    @FXML
    private void buscar(KeyEvent event) {
        
        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if(buscar.isEmpty()){
            
            tblProveedor.setItems(lista);
            
        }
        else{
            
            listaFiltrada.clear();
            for (proveedor listas : lista) {
                
                if(listas.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listas);
                    
                }
                if(listas.getRuc().contains(buscar)){
                    
                    listaFiltrada.add(listas);
                    
                }
                   
                
            }
            
            tblProveedor.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void noMostrarFila(MouseEvent event) {
        
        btnIrA.setDisable(true);
        
        txtNombreProveedor.setDisable(true);
        txtRucProveedor.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarProveedor.setDisable(true);
        btnEliminarProveedor.setDisable(true);
        btnAgregarProveedor.setDisable(false);
        
        
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        
        proveedor prov = tblProveedor.getSelectionModel().getSelectedItem();
        btnIrA.setDisable(false);
        
        txtIdProveedor.setText(String.valueOf(prov.getId()));
        txtNombreProveedor.setText(String.valueOf(prov.getNombre()));
        txtRucProveedor.setText(String.valueOf(prov.getRuc()));
        
        txtNombreProveedor.setDisable(true);
        txtRucProveedor.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarProveedor.setDisable(false);
        btnEliminarProveedor.setDisable(false);
        btnAgregarProveedor.setDisable(false);
        
    }

    @FXML
    private void verInfoCliente(ActionEvent event) {
        
        tabPanePro.getSelectionModel().select(paneInfo);
        
    }

    @FXML
    private void agregar(ActionEvent event) {
        
        txtNombreProveedor.clear();
        txtIdProveedor.clear();
        txtRucProveedor.clear();
        
        modificar = false;
        
        txtNombreProveedor.setDisable(false);
        txtRucProveedor.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificarProveedor.setDisable(true);
        btnEliminarProveedor.setDisable(true);
        btnAgregarProveedor.setDisable(true);
        
    }

    @FXML
    private void modificar(ActionEvent event) {
        
        prov.setId(Integer.parseInt(txtIdProveedor.getText()));
        
        modificar = true;
        
        txtNombreProveedor.setDisable(false);
        txtRucProveedor.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificarProveedor.setDisable(true);
        btnEliminarProveedor.setDisable(true);
        btnAgregarProveedor.setDisable(true);
        
    }

    @FXML
    private void eliminar(ActionEvent event) {
        
        // Obtener el ID del cliente desde el campo correspondiente
        int idPr = Integer.parseInt(txtIdProveedor.getText());

        // Crear una alerta de confirmación
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmar Eliminación");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿Estás seguro de eliminar este proveedor? Esta acción no se puede deshacer.");

        // Mostrar la alerta de confirmación y esperar a que el usuario elija una opción
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // El usuario ha confirmado la eliminación, proceder con la operación
            prov.setId(idPr);

            if (prov.eliminar()) {
                // Mostrar mensaje de eliminación exitosa
                Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
                alertaExito.setTitle("Eliminación Exitosa");
                alertaExito.setHeaderText(null);
                alertaExito.setContentText("Proveedor eliminado correctamente.");
                alertaExito.showAndWait();

                txtNombreProveedor.clear();
                txtIdProveedor.clear();
                txtRucProveedor.clear();

            } else {
                // Mostrar mensaje de error
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setHeaderText(null);
                alertaError.setContentText("No se pudo eliminar el proveedor.");
                alertaError.showAndWait();

                txtNombreProveedor.setText(prov.getNombre());
                txtIdProveedor.setText(String.valueOf(prov.getId()));
                txtRucProveedor.setText(prov.getRuc());

            }

        }

            txtIdProveedor.clear();
            txtNombreProveedor.clear();
            txtRucProveedor.clear();

            txtNombreProveedor.setDisable(true);
            txtRucProveedor.setDisable(true);
            btnAceptar.setDisable(true);
            btnCancelar.setDisable(true);
            btnModificarProveedor.setDisable(true);
            btnEliminarProveedor.setDisable(true);
            btnAgregarProveedor.setDisable(false);
            
            mostrarDatos();

    }

    @FXML
    private void aceptar(ActionEvent event) {
        
        String rucPr = txtRucProveedor.getText();

        if(ValidadorString.isValid(rucPr)){
            prov.setRuc(rucPr);
            prov.setNombre(txtNombreProveedor.getText());

            if(modificar){
                if(prov.modificar()){
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Modificado correctamente");
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido modificar correctamente");
                    alerta.show();
                    
                    txtIdProveedor.setText(String.valueOf(prov.getId()));
                    txtNombreProveedor.setText(String.valueOf(prov.getNombre()));
                    txtRucProveedor.setText(String.valueOf(prov.getRuc()));
                    
                }
            } else {
                if(prov.insertar()){
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Insertado correctamente");
                    alerta.show();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido insertar correctamente");
                    alerta.show();
                    
                    txtIdProveedor.setText(String.valueOf(prov.getId()));
                    txtNombreProveedor.setText(String.valueOf(prov.getNombre()));
                    txtRucProveedor.setText(String.valueOf(prov.getRuc()));
                                      
                }
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setContentText("RUC/CI Invalido");
            alerta.show(); 
        }
        
        txtNombreProveedor.setDisable(true);
        txtRucProveedor.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarProveedor.setDisable(true);
        btnEliminarProveedor.setDisable(true);
        btnAgregarProveedor.setDisable(false);
        
        mostrarDatos();
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
        txtIdProveedor.clear();
        txtNombreProveedor.clear();
        txtRucProveedor.clear();
        
        txtNombreProveedor.setDisable(true);
        txtRucProveedor.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificarProveedor.setDisable(true);
        btnEliminarProveedor.setDisable(true);
        btnAgregarProveedor.setDisable(false);
        
    }
    
    public void mostrarDatos(){
        
        lista = FXCollections.observableArrayList(prov.consulta());
        colRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));    
        tblProveedor.setItems(lista);
        
    }

}
