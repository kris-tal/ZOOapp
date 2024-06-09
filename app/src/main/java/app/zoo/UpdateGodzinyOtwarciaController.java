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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UpdateGodzinyOtwarciaController {

    @FXML
    private Slider dzienSlider;
    @FXML
    private TextField odHourTextField;
    @FXML
    private ChoiceBox<String> odMinutesChoiceBox;
    @FXML
    private TextField doHourTextField;
    @FXML
    private ChoiceBox<String> doMinutesChoiceBox;

    @FXML
    private Button potwierdzButton;

    @FXML
    private void initialize() {
        odMinutesChoiceBox.getItems().addAll("00", "15", "30", "45");
        doMinutesChoiceBox.getItems().addAll("00", "15", "30", "45");

        potwierdzButton.setOnAction(event -> {
            if (validateInput()) {
                updateGodzinyOtwarcia();
            }
        });
    }

    private boolean validateInput() {
        if (odHourTextField.getText().isEmpty() || !odHourTextField.getText().matches("\\d+")
                || doHourTextField.getText().isEmpty() || !doHourTextField.getText().matches("\\d+")) {
            return false;
        }
        Integer odHour = Integer.parseInt(odHourTextField.getText());
        if (odHour < 0 || odHour > 23) { 
            return false;
        }
        Integer doHour = Integer.parseInt(doHourTextField.getText());
        if (doHour < 0 || doHour > 23) { 
            return false;
        }
        return true;
    }


private void updateGodzinyOtwarcia() {
    int dzienTyg = (int) dzienSlider.getValue();
    String otwarcie = String.format("%02d", Integer.parseInt(odHourTextField.getText())) + ":" + odMinutesChoiceBox.getValue();
    String zamkniecie = String.format("%02d", Integer.parseInt(doHourTextField.getText())) + ":" + doMinutesChoiceBox.getValue();

    try (Connection conn = PsqlManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(
                 "UPDATE godziny_otwarcia SET otwarcie = ?, zamkniecie = ? WHERE dzien_tygodnia = ?")) {

        pstmt.setTime(1, java.sql.Time.valueOf(LocalTime.parse(otwarcie)));
        pstmt.setTime(2, java.sql.Time.valueOf(LocalTime.parse(zamkniecie)));
        pstmt.setInt(3, dzienTyg);

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            showAlert("Sukces", "Godziny otwarcia zostały zaktualizowane.", AlertType.INFORMATION);
        } else {
            showAlert("Błąd", "Nie udało się zaktualizować godzin otwarcia.", AlertType.ERROR);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Błąd", "Wystąpił błąd podczas aktualizacji godzin otwarcia.", AlertType.ERROR);
    }
}

private void showAlert(String title, String content, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}

    @FXML
    public static void openGodzinyOtwarcia() {
        SceneLoader.loadNewScene("update-godziny-otwarcia.fxml");
    }
}