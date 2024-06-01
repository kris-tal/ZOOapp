package app.zoo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainPageController {
    @FXML
    private Button mapaZooButton;

    @FXML
    private Button planAktywnosciButton;

    @FXML
    private Button zwierzetaButton;

    @FXML
    private Button pracownicyButton;

    @FXML
    public void initialize() {
        mapaZooButton.setOnAction(event -> openMapaZoo());
        planAktywnosciButton.setOnAction(event -> openPlanAktywnosci());
        zwierzetaButton.setOnAction(event -> openZwierzeta());
        pracownicyButton.setOnAction(event -> openPracownicy());
    }

    private void loadScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Stage currentStage = (Stage) mapaZooButton.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openMapaZoo() {
        loadScene("mapa-zoo.fxml");
    }

    @FXML
    private void openPlanAktywnosci() {
        loadScene("plan-aktywnosci.fxml");
    }

    @FXML
    private void openZwierzeta() {
        loadScene("zwierzeta.fxml");
    }

    @FXML
    private void openPracownicy() {
        loadScene("pracownicy.fxml");
    }
}
