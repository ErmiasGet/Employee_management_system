import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Label activeLabel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label inactiveLabel;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private Label totalEmployee;

    @FXML
    private BorderPane vbox;
    private Parent root;
    private Stage stage;
    private Scene scene;
    DatabaseConnectivty database = new DatabaseConnectivty();
    private  Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    void onLogoutHandler(ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if (option.get().equals(ButtonType.OK)) {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("LoginView.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

        @FXML
    void addEmployeeHandler(ActionEvent event) throws IOException {
        root = (Parent) FXMLLoader.load(getClass().getResource("addEmployeeView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void onEmployeeSalaryHandler(ActionEvent event) throws IOException{
        borderPane.setCenter((Parent) FXMLLoader.load(getClass().getResource("EmployeeSalaryView.fxml")));   
    }

    @FXML
    void onPayrollHandler(ActionEvent event) throws IOException{
        borderPane.setCenter((Parent) FXMLLoader.load(getClass().getResource("Payroll.fxml")));  
    }

    @FXML
    void onHomeHandler(ActionEvent event) throws IOException{
        root = (Parent) FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void homeTotalEmployee() {
        String sql = "select count(id) from Employee_details";
        
        connect = database.getConnection();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            totalEmployee.setText(String.valueOf(countData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void homeEmployeeTotalPresent(){
        String sql = "select count(id) from payroll where gross > 0.0";
        connect = database.getConnection();
        int countData = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery(sql);
            while(result.next()){
                countData = result.getInt("COUNT(id)");
            }
            
            activeLabel.setText(String.valueOf(countData));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void homeTotalInactive(){
        String sql = "select count(id) from payroll where gross = '0.0'";
        connect = database.getConnection();
        int countData = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery(sql);
            while(result.next()){
                countData = result.getInt("COUNT(id)");
            }
            inactiveLabel.setText(String.valueOf(countData));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        homeTotalEmployee();
        homeEmployeeTotalPresent();
        homeTotalInactive();

    }

}
