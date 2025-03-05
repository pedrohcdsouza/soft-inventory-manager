module pedrohcdsouza.softinventorymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens pedrohcdsouza.softinventorymanager to javafx.fxml;
    opens pedrohcdsouza.softinventorymanager.controllers to javafx.fxml;
    opens pedrohcdsouza.softinventorymanager.models to javafx.base;
    exports pedrohcdsouza.softinventorymanager;
}