package pk.wieik.matrix_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource("/pk/wieik/gui/hello-view.fxml");

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        VBox root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Matrix Calculator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}