package pedrohcdsouza.softinventorymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/index.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Gerenciador de Estoque");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}