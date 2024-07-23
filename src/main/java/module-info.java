module com.mycompany.mbareteishon {
    requires javafx.controls;
    requires javafx.fxml;
    //para la base de datos
    requires java.base;
    requires java.sql;

    opens com.mycompany.mbareteishon to javafx.fxml;
    exports com.mycompany.mbareteishon;
    exports com.mycompany.mbareteishon.modelo;
    
}
