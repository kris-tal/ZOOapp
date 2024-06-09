package app.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;

import app.zoo.database.PsqlManager;
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

        });

    }

    private void updateGodzinyOtwarcia() {
        int dzienTyg = (int) dzienSlider.getValue();
        String otwarcie = odHourTextField.getText() + ":" + odMinuteChoiceBox.getValue();
        String zamkniecie = doHourTextField.getText() + ":" + doMinuteChoiceBox.getValue();
    
        try (Connection conn = PsqlManager.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE godziny_otwarcia SET otwarcie = ?, zamkniecie = ? WHERE dzien_tygodnia = ?")) {
    
            pstmt.setTime(1, java.sql.Time.valueOf(LocalTime.parse(otwarcie)));
            pstmt.setTime(2, java.sql.Time.valueOf(LocalTime.parse(zamkniecie)));
            pstmt.setInt(3, dzienTyg);
    
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public static void openGodzinyOtwarcia() {
        SceneLoader.loadNewScene("update-godziny-otwarcia.fxml");
    }
}
