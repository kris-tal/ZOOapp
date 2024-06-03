package app.zoo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import app.zoo.database.Pracownik;

public class ToolBarController {
    private Stage stage;
    private Pracownik pracownik;

    @FXML
    private Button mapaZooButton;

    @FXML
    private Button planAktywnosciButton;

    @FXML
    private Button zwierzetaButton;

    @FXML
    private Button pracownicyButton;

    @FXML
    private Button wylogujButton;

    public void setPracownik(Pracownik pracownik2) {
        this.pracownik = pracownik2;
    }

    @FXML
    public void initialize() {
        mapaZooButton.setOnAction(event -> openMapaZoo());
        planAktywnosciButton.setOnAction(event -> openPlanAktywnosci());
        zwierzetaButton.setOnAction(event -> openZwierzeta());
        pracownicyButton.setOnAction(event -> openPracownicy());
        wylogujButton.setOnAction(event -> openLogin());
    }

    public Pracownik getPracownik() {
        return this.pracownik;
    }

    @FXML
    private void openMapaZoo() {
        SceneLoader.loadScene("mapa-zoo.fxml", (Stage)mapaZooButton.getScene().getWindow());
    }

    @FXML
    private void openPlanAktywnosci() {
        SceneLoader.loadScene("plan-aktywnosci.fxml", (Stage)planAktywnosciButton.getScene().getWindow());
    }

    @FXML
    private void openZwierzeta() {
        SceneLoader.loadScene("zwierzeta.fxml", (Stage)zwierzetaButton.getScene().getWindow());
    }

    @FXML
    private void openPracownicy() {
        SceneLoader.loadScene("pracownicy.fxml", (Stage)pracownicyButton.getScene().getWindow());
    }

    private void openLogin() {
        pracownik = null;
        SceneLoader.loadScene("login.fxml", (Stage)wylogujButton.getScene().getWindow());
    }
}
