package app.zoo;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class FiltrujController {
    @FXML
    public static void openFiltruj() {
        SceneLoader.loadScene("Filtruj.fxml", new Stage());
    }
}
