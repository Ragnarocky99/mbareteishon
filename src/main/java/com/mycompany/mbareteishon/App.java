package com.mycompany.mbareteishon;

import com.mycompany.mbareteishon.modelo.empleado;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    empleado emp = new empleado();
    ArrayList<empleado> empleados = new ArrayList<>();
    boolean flag;

    @Override
    public void start(Stage stage) throws IOException {
        
        empleados = emp.consulta();
        
        for (empleado empl : empleados) {
            System.out.println(empl.getCargo());
            if(empl.getCargo().equals("Admin")){
                flag = true;
                break;
            }
            
        }
        
        if(flag){
            scene = new Scene(loadFXML("vistaLogin"));
                stage.setScene(scene);
        }else{
            scene = new Scene(loadFXML("vistaRegistro"));
                stage.setScene(scene);
        }
        
        //no se detecto un admin
        
        // Maximizar la ventana
        //stage.setMaximized(true);

        // Deshabilitar el cambio de tamaño
        stage.setResizable(false);

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}