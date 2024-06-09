package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FiltrujController {
    @FXML
    private Button potwierdzButton;
    @FXML
    private TextField imieTextField;
    @FXML
    private TextField nazwiskoTextField;

    private String filtruj() {
        String imie = imieTextField.getText();
        String nazwisko = nazwiskoTextField.getText();
        String filtr = "";

        if (!imie.isEmpty() && !nazwisko.isEmpty()) {
            filtr = "WHERE imie = " + imie + " AND nazwisko = " + nazwisko;
            // filtruj po imieniu tzn jak masz ten string ktorym filtrujesz to dodaj sobie warunek na imie
        }
        else if(!imie.isEmpty()) {
            filtr = "WHERE imie = " + imie;
        }
        else if(!nazwisko.isEmpty()) {
            filtr = "WHERE nazwisko = " + nazwisko;    //nie wiem dokladnie w jakiej formie potrzebujesz tych stringow
        }

        return filtr;
    }

    @FXML
    private void initialize() {
        potwierdzButton.setOnAction(event -> filtruj());
    }

    @FXML
    public static void openFiltruj() {
        SceneLoader.loadScene("filtruj.fxml", new Stage());
    }
}
