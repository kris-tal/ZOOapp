package app.zoo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.zoo.database.MojPracownik;
import app.zoo.database.MyController;
import app.zoo.database.PsqlManager;
import app.zoo.services.Find;
import app.zoo.database.Pracownik;

public class LoginController {
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> onLoginButtonClick());
    }

    void openMainPage(Pracownik pracownik) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
            Parent root = fxmlLoader.load();
            MainPageController mainPageController = fxmlLoader.getController();
            mainPageController.setPracownik(pracownik);
            mainPageController.updateUserDetails();
            Stage currentStage = (Stage) submitButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int userID;
    private String password;
    
    @FXML
    protected void onLoginButtonClick() {
        System.out.println("Username: " + loginField.getText());
        try {
            userID = Integer.parseInt(loginField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID");
            return;
        }
        System.out.println("Password: " + passwordField.getText());
        password = passwordField.getText();
        try {
            if(Find.findEmployeeByUserIDAndPassword(userID, password) != null) {
                Pracownik p = Find.findEmployeeByUserIDAndPassword(userID, password);
                MojPracownik pracownik = new MojPracownik(p.getID(), p.getImie(), p.getNazwisko(), p.getPesel(), p.getHaslo());
                openMainPage(p);
            } else {
                System.out.println("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
