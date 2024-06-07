package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajController extends ToolBarController {
    @FXML
    private TextField pole1;

    @FXML
    private TextField pole2;

    @FXML
    private TextField pole3;

    @FXML
    private TextField pole4;

    @FXML
    private TextField pole5;

    @FXML
    private TextField pole6;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    static public void openDodaj(Stage stage) {
        SceneLoader.loadScene("dodaj.fxml", stage);
    }


}
