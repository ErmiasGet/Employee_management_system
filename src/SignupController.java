
// import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField UnTextField;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton clearBtn;

    @FXML
    private PasswordField enterPasswordTF;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField recoveryKey;

    @FXML
    private JFXButton signupBtn;

    DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection con = db.getConnection();

    @FXML
    void onBackHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if (option.get().equals(ButtonType.OK)) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Logout Successfully!");
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("Manager.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void onClearHandler(ActionEvent event) {
        firstNameTextField.clear();
        lastNameTextField.clear();
        UnTextField.clear();
        enterPasswordTF.clear();
        recoveryKey.clear();
    }

    @FXML
    void onSignupHandler(ActionEvent event) {
        try {

            int result = con.createStatement()
                    .executeUpdate("insert into admin  values(" +
                            "'" + firstNameTextField.getText() + "', " +
                            "'" + lastNameTextField.getText() + "', " +
                            "'" + UnTextField.getText() + "', " +
                            "'" + enterPasswordTF.getText() + "', " +
                            "'" + recoveryKey.getText() + "')");

            if (result == 1) {
                displayAlert(AlertType.INFORMATION, " Added Succesfuly", "New Entry Successfully Added.");
            } else {
                displayAlert(AlertType.ERROR, "Entry Not Added", "Unable to Add Entry.");
            }
            onClearHandler(event);

        } catch (Exception e) {

            e.printStackTrace();

            displayAlert(AlertType.ERROR, "Error", "Unable to Add");

        }
    }

    private void displayAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
