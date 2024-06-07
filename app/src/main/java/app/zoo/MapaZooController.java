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
    public void openDodaj() {
        try {
            SceneLoader.loadScene("dodaj.fxml", new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openEdytuj() {
        try {
            SceneLoader.loadScene("edytuj.fxml", new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void usunKrotke() {  //tu jako argument bedzie jakas krotka cze cos
        //usuniecie krotki z bazy danych
    }

    @Override
    public void initialize() {
        super.initialize();
        dodajButton.setOnAction(event -> openDodaj());
        edytujButton.setOnAction(event -> openEdytuj());
        usunButton.setOnAction(event -> usunKrotke());

    }
}
