/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.producto;
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
public class VistaGestionArticulosController implements Initializable {


    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<producto> tblProducto;
    @FXML
    private TableColumn<producto, Integer> colIdProducto;
    @FXML
    private TableColumn<producto, String> colDescProducto;
    @FXML
    private Button btnIrAPedido;
    @FXML
    private TextField txtIdProducto;
    @FXML
    private TextField txtDescProducto;
    @FXML
    private TextField txtCosto;
    @FXML
    private TextField txtPrecioVenta;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Tab tabInfo;
    private TextField txtNeto;
    @FXML
    private TextField txtStock;
    
    producto pro = new producto();
    ObservableList<producto> lista;
    ObservableList<producto> listaFiltrada;
    
    private boolean modificar;
    @FXML
    private TabPane tabProductos;
    @FXML
    private TextField txtNombrePro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtNombrePro.setDisable(true);
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(true);   
        txtPrecioVenta.setDisable(true);
        txtCosto.setDisable(true);
        txtStock.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(false);
        mostrarDatos();
        
        
    }    

    @FXML
    private void noMostrarFila(MouseEvent event) {
        
        txtNombrePro.setDisable(true);
        btnIrAPedido.setDisable(true);
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(true);   
        txtPrecioVenta.setDisable(true);
        txtCosto.setDisable(true);
        txtStock.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(false);
        
    }

    @FXML
    private void mostrarFila(MouseEvent event) {
        
        producto pro = tblProducto.getSelectionModel().getSelectedItem();
        btnIrAPedido.setDisable(false);
        
        txtNombrePro.setText(String.valueOf(pro.getNombre()));
        txtIdProducto.setText(String.valueOf(pro.getId()));
        txtDescProducto.setText(String.valueOf(pro.getDesc()));
        txtPrecioVenta.setText(String.valueOf(pro.getPrecio()));
        txtCosto.setText(String.valueOf(pro.getCosto()));
        txtStock.setText(String.valueOf(pro.getStock()));
        
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(true);   
        txtPrecioVenta.setDisable(true);
        txtCosto.setDisable(true);
        txtStock.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(false);
        btnEliminar.setDisable(false);
        btnAgregar.setDisable(false);
        
    }

    @FXML
    private void buscar(KeyEvent event) {
        
        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if(buscar.isEmpty()){
            
            tblProducto.setItems(lista);
            
        }
        else{
            
            listaFiltrada.clear();
            for (producto listas : lista) {
                
                if(listas.getDesc().toLowerCase().contains(buscar.toLowerCase())){
                    
                    listaFiltrada.add(listas);
                    
                }
                if(String.valueOf(listas.getId()).contains(buscar)){
                    
                    listaFiltrada.add(listas);
                    
                }
                   
                
            }
            
            tblProducto.setItems(listaFiltrada);
            
        }
        
    }

    @FXML
    private void actionIrA(ActionEvent event) {
        
        tabProductos.getSelectionModel().select(tabInfo);
        
    }


    @FXML
    private void actionAceptar(ActionEvent event) {
        
            pro.setNombre(txtNombrePro.getText());
            pro.setDesc(txtDescProducto.getText());
            pro.setPrecio(Double.parseDouble(txtPrecioVenta.getText()));
            pro.setCosto(Double.parseDouble(txtCosto.getText()));
            pro.setStock(Integer.parseInt(txtStock.getText()));
            
            if(modificar){
                if(pro.getPrecio() > 0 && pro.getCosto() > 0 && pro.getStock() > 0 && !"".equals(pro.getNombre()) && !"".equals(pro.getDesc())){
                    if(pro.modificar()){
                        
                       Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setHeaderText(null);
                        alerta.setContentText("Modificado correctamente");
                        alerta.show(); 
                        
                    }else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("No se ha podido modificar correctamente");
                    alerta.show();
                    
                    txtIdProducto.setText(String.valueOf(pro.getId()));
                    txtDescProducto.setText(String.valueOf(pro.getDesc()));
                    txtPrecioVenta.setText(String.valueOf(pro.getPrecio()));
                    txtCosto.setText(String.valueOf(pro.getCosto()));
                    txtStock.setText(String.valueOf(pro.getStock()));
                    
                    } 
                    
                }else{
                    
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Valores Fuera Incompletos");
                    alerta.show();
                    
                }
            } else {
                if(pro.getPrecio() > 0 && pro.getCosto() > 0 && pro.getStock() > 0 && !"".equals(pro.getNombre()) && !"".equals(pro.getDesc())){
                    
                    if(pro.insertar()){
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
                    
                }else{
                    
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText(null);
                    alerta.setContentText("Valores Fuera de Rango");
                    alerta.show();
                    
                }
            }
            
            mostrarDatos();
            
            txtNombrePro.setDisable(true);
            txtIdProducto.setDisable(true);
            txtDescProducto.setDisable(true);   
            txtPrecioVenta.setDisable(true);
            txtCosto.setDisable(true);
            txtStock.setDisable(true);
            btnAceptar.setDisable(true);
            btnCancelar.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnAgregar.setDisable(false);
        
    }

    @FXML
    private void actionCancelar(ActionEvent event) {
        
        modificar = true;
        
        txtNombrePro.setDisable(true);
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(true);   
        txtPrecioVenta.setDisable(true);
        txtCosto.setDisable(true);
        txtStock.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(false);
        mostrarDatos();
        
        txtNombrePro.clear();
        txtIdProducto.clear();
        txtDescProducto.clear();   
        txtPrecioVenta.clear();
        txtCosto.clear();
        txtStock.clear();
        
    }
    

    @FXML
    private void actionAgregar(ActionEvent event) {
        
        txtNombrePro.clear();
        txtIdProducto.clear();
        txtDescProducto.clear();   
        txtPrecioVenta.clear();
        txtCosto.clear();
        txtStock.clear();
        
        modificar = false;
        
        txtNombrePro.setDisable(false);
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(false);   
        txtPrecioVenta.setDisable(false);
        txtCosto.setDisable(false);
        txtStock.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(true);
        
    }

    @FXML
    private void actionModificar(ActionEvent event) {
        
        pro.setId(Integer.parseInt(txtIdProducto.getText()));
        
        modificar = true;
        
        txtNombrePro.setDisable(false);
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(false);   
        txtPrecioVenta.setDisable(false);
        txtCosto.setDisable(false);
        txtStock.setDisable(false);
        btnAceptar.setDisable(false);
        btnCancelar.setDisable(false);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(true);
        
    }

    @FXML
    private void actionEliminar(ActionEvent event) {
        
        modificar = false;
        // Obtener el ID del producto desde el campo correspondiente
        int idProducto = Integer.parseInt(txtIdProducto.getText());

        // Crear una alerta de confirmación
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle("Confirmar Eliminación");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿Estás seguro de eliminar este cliente? Esta acción no se puede deshacer.");

        // Mostrar la alerta de confirmación y esperar a que el usuario elija una opción
        Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // El usuario ha confirmado la eliminación, proceder con la operación
            pro.setId(idProducto);

            if (pro.eliminar()) {
                // Mostrar mensaje de eliminación exitosa
                Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
                alertaExito.setTitle("Eliminación Exitosa");
                alertaExito.setHeaderText(null);
                alertaExito.setContentText("Producto eliminado correctamente.");
                alertaExito.showAndWait();

                txtNombrePro.clear();
                txtIdProducto.clear();
                txtDescProducto.clear();   
                txtPrecioVenta.clear();
                txtCosto.clear();
                txtStock.clear();

            } else {
                // Mostrar mensaje de error
                Alert alertaError = new Alert(Alert.AlertType.ERROR);
                alertaError.setTitle("Error");
                alertaError.setHeaderText(null);
                alertaError.setContentText("No se pudo eliminar el producto.");
                alertaError.showAndWait();

                txtNombrePro.setText(String.valueOf(pro.getNombre()));
                txtIdProducto.setText(String.valueOf(pro.getId()));
                txtDescProducto.setText(pro.getDesc());   
                txtPrecioVenta.setText(String.valueOf(pro.getPrecio()));
                txtCosto.setText(String.valueOf(pro.getCosto()));
                txtStock.setText(String.valueOf(pro.getStock()));

            }
        }
        
        txtNombrePro.setDisable(true);
        txtIdProducto.setDisable(true);
        txtDescProducto.setDisable(true);   
        txtPrecioVenta.setDisable(true);
        txtCosto.setDisable(true);
        txtStock.setDisable(true);
        btnAceptar.setDisable(true);
        btnCancelar.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAgregar.setDisable(false);
        
        txtNombrePro.clear();
        txtIdProducto.clear();
        txtDescProducto.clear();   
        txtPrecioVenta.clear();
        txtCosto.clear();
        txtStock.clear();
        
        mostrarDatos();
        
    }

    
    public void mostrarDatos(){
        
        lista = FXCollections.observableArrayList(pro.consulta());
        colDescProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("id"));    
        tblProducto.setItems(lista);
        
    }
    
}
