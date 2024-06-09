package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdatePoziomController {

    @FXML
    private Button potwierdzButton;
    @FXML
    private TextField idTextField;
    @FXML
    private Slider poziomSlider;

    @FXML
    private void initialize() {
        potwierdzButton.setDisable(true);
        if(!idTextField.getText().isEmpty() || idTextField.getText().matches("\\d+")) {
            potwierdzButton.setDisable(false);
        }
        int poziom = (int)poziomSlider.getValue();
        potwierdzButton.setOnAction(event -> {
            Integer id = Integer.parseInt(idTextField.getText());
            System.out.println("Zmieniono poziom zwierzecia o id: " + id + " na poziom: " + poziom);
            //update poziom zwierzecia o tym id na poziom
        });

    }

    @FXML
    public static void updatujPoziom() {
        SceneLoader.loadNewScene("update-poziom.fxml");
    }
}
