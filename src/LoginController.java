import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private JFXButton clearBtn;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Hyperlink forgotHL;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXPasswordField passwordTF;

    @FXML
    private JFXTextField usernameTF;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection con = db.getConnection();
    private ResultSet resultSet;

    @FXML
    void onBackHandler(ActionEvent event) throws IOException {
      root = (Parent) FXMLLoader.load(getClass().getResource("MainLoginView.fxml"));
      stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }

    @FXML
    void onClearHandler(ActionEvent event) {
      usernameTF.clear();
       passwordTF.clear();
    }

    @FXML
    void onForgotHandler(ActionEvent event) throws IOException{
        borderPane.setCenter((Parent) FXMLLoader.load(getClass().getResource("Recovery.fxml")));  
    }


    @FXML
    void onLoginHandler(ActionEvent event) throws Exception {
    try {
        resultSet = con.createStatement().executeQuery("select * from admin where Username = '" +
                usernameTF.getText() + "' and Password = '" +
                passwordTF.getText() + "'");

        if (!resultSet.next()) {
            usernameTF.clear();
            passwordTF.clear();
            displayAlert(AlertType.ERROR, "Invalid info", "Enter your username and password correctly");
        } else {
            root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            displayAlert(AlertType.INFORMATION, "Login Successfully", "Welcome To Employee Management System");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (resultSet != null) {
            resultSet.close();
        }
    }
}



    private void displayAlert(AlertType type, String title, String message) {
           Alert alert = new Alert(type);
           alert.setTitle(title);
           alert.setContentText(message);
           alert.showAndWait();
    }


}

