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

    void openMainPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
            Parent root = fxmlLoader.load();
            MainPageController mainPageController = fxmlLoader.getController();
            mainPageController.setUser(user);
            mainPageController.updateUserDetails();
            Stage currentStage = (Stage) submitButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User user;
    private String userID;
    private int password;

    @FXML
    protected void onLoginButtonClick() {
        System.out.println("Username: " + loginField.getText());
        userID = loginField.getText();
        System.out.println("Password: " + passwordField.getText());
        password = passwordField.getText().hashCode();
        System.out.println("Hashed password: " + password);
        if(true) {
            user = new User(userID,"zarządca"); //tu bedzie wyciagniete z bazy stanowisko
            openMainPage();
        }
        else {
            System.out.println("Invalid credentials");
        }
    }
}
