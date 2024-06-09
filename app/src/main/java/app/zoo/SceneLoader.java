package app.zoo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public static void loadScene(String fxml, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadNewScene(String fxml) {
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
