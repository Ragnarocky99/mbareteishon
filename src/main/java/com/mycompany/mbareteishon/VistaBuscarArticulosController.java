package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.producto;
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

public class VistaBuscarArticulosController implements Initializable {

    @FXML
    private TableView<producto> tblProductos;
    @FXML
    private TableColumn<producto, Integer> colIdProducto;
    @FXML
    private TableColumn<producto, String> colDescripcion;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnBuscar;

    private Stage stage;
    private VistaPedidosController controladorPedidos;

    ObservableList<producto> lista;
    ObservableList<producto> listaFiltrada;

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
        producto pro = tblProductos.getSelectionModel().getSelectedItem();
        btnAceptar.setDisable(false);
    }

    @FXML
    private void buscar(ActionEvent event) {
        lista = FXCollections.observableArrayList(new producto().consulta());
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("id"));    
        tblProductos.setItems(lista);

        listaFiltrada = FXCollections.observableArrayList();
        String buscar = txtBuscar.getText();
        if (buscar.isEmpty()) {
            tblProductos.setItems(lista);
        } else {
            listaFiltrada.clear();
            for (producto listas : lista) {
                if (listas.getDesc().toLowerCase().contains(buscar.toLowerCase())) {
                    listaFiltrada.add(listas);
                }
                if (String.valueOf(listas.getId()).contains(buscar)) {
                    listaFiltrada.add(listas);
                }
            }
            tblProductos.setItems(listaFiltrada);
        }
    }

    @FXML
    private void aceptar(ActionEvent event) {
        producto pro = tblProductos.getSelectionModel().getSelectedItem();
        if (pro != null && controladorPedidos != null) {
            controladorPedidos.setProductoSeleccionado(pro);
        }
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        producto pro = new producto();
        pro.setCosto(0.0);
        pro.setId(-1);
        if (pro != null && controladorPedidos != null) {
            controladorPedidos.setProductoSeleccionado(pro);
        }
        stage.close();
    }

    @FXML
    private void noMostrarFila(MouseEvent event) {
        
        btnAceptar.setDisable(false);
        
    }
}
