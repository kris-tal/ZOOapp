package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class UpdateGodzinyOtwarciaController {

    @FXML
    private Slider dzienSlider;
    @FXML
    private TextField odHourTextField;
    @FXML
    private ChoiceBox<String> odMinuteChoiceBox;
    @FXML
    private TextField doHourTextField;
    @FXML
    private ChoiceBox<String> doMinuteChoiceBox;

    @FXML
    private Button potwierdzButton;


    @FXML
    private void initialize() {
        odMinuteChoiceBox.getItems().addAll("00", "15", "30", "45");
        doMinuteChoiceBox.getItems().addAll("00", "15", "30", "45");

        potwierdzButton.setOnAction(event -> {
            if(odHourTextField.getText().isEmpty() || !odHourTextField.getText().matches("\\d+")
            || doHourTextField.getText().isEmpty() || !doHourTextField.getText().matches("\\d+")) {
                return;
            }
            Integer odHour = Integer.parseInt(odHourTextField.getText());
            if(odHour < 0 || odHour > 24) {
                return;
            }
            Integer doHour = Integer.parseInt(doHourTextField.getText());
            if(doHour < 0 || doHour > 24) {
                return;
            }
            int dzienTyg = (int)dzienSlider.getValue();

            //update poziom zwierzecia o tym id na poziom
        });

    }

    @FXML
    public static void openGodzinyOtwarcia() {
        SceneLoader.loadNewScene("update-godziny-otwarcia.fxml");
    }
}
