import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MainLoginController {

    @FXML
    private JFXButton adminBtn;

    @FXML
    private JFXButton managerBtn;

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    void onAdminHandler(ActionEvent event) throws IOException {
        root = (Parent) FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onManagerHandler(ActionEvent event) throws IOException{
        root = (Parent) FXMLLoader.load(getClass().getResource("Manager.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
