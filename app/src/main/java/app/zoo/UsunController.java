package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class UsunController {
    @FXML
    private ComboBox tabelaComboBox;
    @FXML
    private Button potwierdzButton;

    private void usunKrotke() {

    }

    @FXML
    public void initialize() {

        potwierdzButton.setOnAction(event -> usunKrotke());
    }

    static public void openUsun(//tutaj dodaj) {
        //SceneLoader.loadScene("usun.fxml", new Stage());
    }
}
