import java.sql.Connection;
import java.sql.ResultSet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RecoveryController {

    @FXML
    private JFXButton onSubmitHandler;


    @FXML
    private JFXTextField recoveryTf;
    private DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection con = db.getConnection();
    private ResultSet resultSet;
    private Parent root;
    private Stage stage;
    private Scene scene;
    

        @FXML
    void onSubmitHandler(ActionEvent event) {
        try {
            resultSet = con.createStatement().executeQuery("select * from admin where RecoveryKey = '" +
                    recoveryTf.getText() + "'");

            if (resultSet == null) {

                recoveryTf.clear();
                  displayAlert(AlertType.ERROR, "Invalid info", "Enter your recovery key correctly");
            } else {

                while (resultSet.next()) {
                    root = (Parent) FXMLLoader.load(getClass().getResource("Password.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

        private void displayAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
