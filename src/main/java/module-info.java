module com.example.ces {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ces to javafx.fxml;
    exports com.example.ces;
    exports com.example.ces.model;
    opens com.example.ces.model to javafx.fxml;
}