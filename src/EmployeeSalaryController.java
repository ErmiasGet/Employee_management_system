import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmployeeSalaryController{

    @FXML
    private TableColumn<EmployeeModel, Integer> OverTimeCol;

    @FXML
    private JFXButton clearBtn;

    @FXML
    private TableColumn<EmployeeModel, Integer> employeeCol;

    @FXML
    private TableColumn<EmployeeModel, String> firstNameCol;

    @FXML
    private TableColumn<EmployeeModel, String> lastNameCol;

    @FXML
    private TableView<EmployeeModel> tableView;

    @FXML
    private TextField tfEmployee;

    @FXML
    private TextField tfFname;

    @FXML
    private TextField tfLname;

    @FXML
    private TextField tfSalary;

    @FXML
    private TextField tfVenue;

    @FXML
    private TableColumn<EmployeeModel, Double> totalSalaryCol;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private TableColumn<EmployeeModel, Double> venueCol;
    private Parent root;
    private Stage stage;
    private Scene scene;

    private ObservableList<EmployeeModel> employee = FXCollections.observableArrayList();
    private DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection con = db.getConnection();
    private ResultSet resultSet;
    double payable;

    @FXML
    void onClearHandler(ActionEvent event) {
        tfEmployee.clear();
        tfFname.clear();
        tfLname.clear();
        tfVenue.clear();
        tfSalary.clear();
    }

    @FXML
    void onUpdateBtn(ActionEvent event) {

        try {
            int result = con.createStatement().executeUpdate
    
            ("update payroll set firstName=" + "'" + tfFname.getText() + "'," +
    
                    " lastName=" + "'" + tfLname.getText() + "'," +
    
                    " gross =" + "'" + tfVenue.getText() + "',"+
    
                    " overTime=" + "'" + tfSalary.getText() + "'" + "where id= " + "'" + tfEmployee.getText() + "'");
            if (result == 1) {
                displayAlert(AlertType.CONFIRMATION, "Updated", "Updated Succesfuly");
            } else {
                displayAlert(AlertType.ERROR, "Error", "Unable to Update");
            }
    
            tableView.getItems().clear();
    
            // getAllInfo();
            // String sql = "select * from payroll";
            salaryListData();
                
        } catch (Exception e) {
    
            e.printStackTrace();
        }
    }

    @FXML
    void onForgotHandler(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if (option.get().equals(ButtonType.OK)) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Deleted!");
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("addEmployeeView.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    public void selectFirstEntry() {
    tableView.getSelectionModel().selectFirst();
}

    public void displayEmployee(EmployeeModel eModel) {
        if (eModel != null) {
            tfEmployee.setText(eModel.getId().toString());
            tfFname.setText(eModel.getFirstName());
            tfLname.setText(eModel.getLastName());
            tfVenue.setText(eModel.getGross().toString());
            tfSalary.setText(eModel.getOverTime().toString());

        } else {
            tfEmployee.clear();
            tfFname.clear();
            tfLname.clear();
            tfVenue.clear();
            tfSalary.clear();
        }
    }

    // private Connection connect;
    // private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    public void salaryReset() {
        tfEmployee.setText("");
        tfFname.setText("");
        tfLname.setText("");
        tfVenue.setText("");
        tfSalary.setText("");
        // salary_salary.setText("");
        
    }
    
    public ObservableList<EmployeeModel> salaryListData() {
        ObservableList<EmployeeModel> listData = FXCollections.observableArrayList();
        
        String sql = "select * from payroll";
        
        try {
            prepare = con.prepareStatement(sql);
            result = prepare.executeQuery();
            
            EmployeeModel employeeD;
            
            while (result.next()) {
                employeeD = new EmployeeModel(
                        result.getInt("id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getDouble("gross"),
                        result.getInt("overTime"));
                
                listData.add(employeeD);
                
            }
            tableView.setItems(listData);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<EmployeeModel> salaryList;

    public void initialize() {
        salaryList = salaryListData();
        getAllInfo();
        
        employeeCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("lastName"));
        totalSalaryCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Double>("gross"));
        OverTimeCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("overTime"));

        tableView.setItems(salaryList);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (ob, oldValue, newValue) -> {
                    displayEmployee(newValue);
                });
                tfEmployee.setDisable(false);
                tfEmployee.setEditable(false);
        getAllInfo();
    }

    private void getAllInfo() {
        try {
            resultSet = con.createStatement().executeQuery("select * from payroll");

            while (resultSet.next()) {

                employee.add(new EmployeeModel(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDouble("gross"),
                        resultSet.getInt("overTime")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
