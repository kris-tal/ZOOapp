package app.zoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import app.zoo.database.PsqlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdatePoziomController {

    @FXML
    private Button potwierdzButton;
    @FXML
    private TextField idTextField;
    @FXML
    private Slider poziomSlider;

    @FXML
    private void initialize() {
        
        potwierdzButton.setOnAction(event -> {
            if(idTextField.getText().isEmpty() || !idTextField.getText().matches("\\d+")) {
                return;
            }
            Integer id = Integer.parseInt(idTextField.getText());
            int poziom = (int)poziomSlider.getValue();
            System.out.println("Zmieniono poziom zwierzecia o id: " + id + " na poziom: " + poziom);
            updateAnimalLevel(id, poziom);
            //update poziom zwierzecia o tym id na poziom
        });

    }

    private void updateAnimalLevel(int id, int poziom) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        conn = PsqlManager.getConnection();
        String sql = "UPDATE zwierzeta SET poziom_umiejetnosci = ? WHERE id = ?";
        pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, poziom);
        pstmt.setInt(2, id);

        int affectedRows = pstmt.executeUpdate();
        System.out.println("Zmieniono poziom zwierzecia o id: " + id + " na poziom: " + poziom + ". Zaktualizowano rekordów: " + affectedRows);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    @FXML
    public static void updatujPoziom() {
        SceneLoader.loadNewScene("update-poziom.fxml");
    }
}
