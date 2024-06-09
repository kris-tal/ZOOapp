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
        StringBuffer s = new StringBuffer();
        for(String uprawnienie : MojPracownik.uprawnienia) {
            s.append(uprawnienie + ", ");
        }
        stanowiskoLabel.setText("Stanowisko: "+ s);
        uprawnieniaLabel.setText("Uprawnienia: " + MojPracownik.getZarzadca());
    }
}

