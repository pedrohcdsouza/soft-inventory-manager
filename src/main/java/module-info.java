module pedrohcdsouza.softinventorymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens pedrohcdsouza.softinventorymanager to javafx.fxml;
    exports pedrohcdsouza.softinventorymanager;
}