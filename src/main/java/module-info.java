module org.example.chefmate {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.jfoenix;
    requires java.sql;
    requires jbcrypt;


    opens Controller to javafx.fxml;
    exports Controller;
    opens LoginRegister to javafx.fxml;
    exports Appli;
    opens Appli to javafx.fxml;
}