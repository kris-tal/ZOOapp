package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DodajController extends ToolBarController {
    @FXML
    private TextField pole1;
    @FXML
    private TextField pole2;
    @FXML
    private TextField pole3;
    @FXML
    private TextField pole4;
    @FXML
    private TextField pole5;
    @FXML
    private TextField pole6;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private CheckBox iloscCheckBox;
    @FXML
    private TextField iloscTextField;
    @FXML
    private ComboBox tabelaComboBox;

    @Override
    public void initialize() {
        super.initialize();
        int columnNumber; //
        ArrayList<String> columnNames = new ArrayList<>(); //
        iloscCheckBox.setDisable(true);
        iloscTextField.setDisable(true);
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6};
        Label[] etykiety = {label1, label2, label3, label4, label5, label6};
        for(TextField pole : pola) {
            pole.setDisable(true);
        }
        //z tym ze nie liczysz id jako kolumny co nie, bo tego nie wybieramy
        //musisz mi ustawic columnNumber na ilosc kolumn w tabeli 42
        columnNumber = 5; //na razie stala bo intellij krzyczy ze niezainicjalizowana
        //i zrobic liste nazw tych kolumn 43
        for(int i = 0; i < columnNumber; i++) {
            pola[i].setDisable(false);
            etykiety[i].setText(columnNames.get(i));
        }


    }

    static public void openDodaj(Stage stage) {
        SceneLoader.loadScene("dodaj.fxml", stage);
    }


}
