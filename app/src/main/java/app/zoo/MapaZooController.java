package app.zoo;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MapaZooController extends ToolBarController{
    @FXML
    private Button dodajButton;
    @FXML
    private Button edytujButton;
    @FXML
    private Button usunButton;

    @FXML
    public void usunKrotke() {  //tu jako argument bedzie jakas krotka cze cos
        //usuniecie krotki z bazy danych
    }

    @Override
    public void initialize() {
        super.initialize();
        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage)dodajButton.getScene().getWindow()));
        edytujButton.setOnAction(event -> EdytujController.openEdytuj((Stage)edytujButton.getScene().getWindow()));
        usunButton.setOnAction(event -> usunKrotke());

    }
}
