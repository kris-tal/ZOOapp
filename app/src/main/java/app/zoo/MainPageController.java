package app.zoo;


import app.zoo.database.MojPracownik;
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
        idLabel.setText("ID: " + super.getPracownik().getID());
        stanowiskoLabel.setText("Stanowisko: "+ MojPracownik.getRole());
        uprawnieniaLabel.setText("Uprawnienia: " + MojPracownik.getZarzadca());
    }
}

