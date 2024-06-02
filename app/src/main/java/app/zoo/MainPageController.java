package app.zoo;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainPageController extends ToolBarController{
    @FXML
    private Label idLabel;

    @FXML
    private Label stanowiskoLabel;

    @FXML
    private Label uprawnieniaLabel;

    public void updateUserDetails() {
        idLabel.setText("ID: " + super.getUser().getID());
        stanowiskoLabel.setText("Stanowisko: " + super.getUser().getRole());
        uprawnieniaLabel.setText("Uprawnienia: " + super.getUser().getPermissions());
    }
}

