package app.zoo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ToolBarController {
    private Stage stage;
    private User user;

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

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        mapaZooButton.setOnAction(event -> openMapaZoo());
        planAktywnosciButton.setOnAction(event -> openPlanAktywnosci());
        zwierzetaButton.setOnAction(event -> openZwierzeta());
        pracownicyButton.setOnAction(event -> openPracownicy());
        wylogujButton.setOnAction(event -> openLogin());
    }

    public User getUser() {
        return this.user;
    }

    private void loadScene(String fxml, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openMapaZoo() {
        loadScene("mapa-zoo.fxml", (Stage)mapaZooButton.getScene().getWindow());
    }

    @FXML
    private void openPlanAktywnosci() {
        loadScene("plan-aktywnosci.fxml", (Stage)planAktywnosciButton.getScene().getWindow());
    }

    @FXML
    private void openZwierzeta() {
        loadScene("zwierzeta.fxml", (Stage)zwierzetaButton.getScene().getWindow());
    }

    @FXML
    private void openPracownicy() {
        loadScene("pracownicy.fxml", (Stage)pracownicyButton.getScene().getWindow());
    }

    @FXML
    private void openLogin() {
        user = null;
        loadScene("login.fxml", (Stage)wylogujButton.getScene().getWindow());
    }
}
