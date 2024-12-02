import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PasswordController {

    @FXML
    private JFXPasswordField passwordTf;

    @FXML
    private JFXPasswordField reEnterPassTf;

    @FXML
    private JFXPasswordField recoveryKey;

    private DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection con = db.getConnection();
    private Parent root;
    private Stage stage;
    private Scene scene;



    @FXML
    void onConfirmHandler(ActionEvent event) throws IOException {
        
        if (passwordTf.getText().equals(reEnterPassTf.getText())) {
            try {
                    int result = con.createStatement().executeUpdate("update admin set  password= '" + passwordTf.getText() + "'" + "where recoveryKey= " + "'" + recoveryKey.getText() + "'");
                    displayAlert(AlertType.CONFIRMATION, "Updated", "Updated Successfully");
                    root = (Parent) FXMLLoader.load(getClass().getResource("LoginView.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            
          catch (SQLException e) {
                e.printStackTrace();
            }
        
        } else {
            displayAlert(AlertType.ERROR, "Error", "Enter the password and confirm correctly!!");
            passwordTf.clear();
            reEnterPassTf.clear();
        }
    }
    
    
private void displayAlert(AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.show();
}
}

