import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EmployeeController {
     @FXML
    private TableColumn<EmployeeModel, Integer> IdCol;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ComboBox<?> genderCB;

    @FXML
    private TableColumn<EmployeeModel, String> dateMemberCol;

    @FXML
    private TextField employeeIdTf;

    @FXML
    private TableColumn<EmployeeModel, String> fnameCol;

    @FXML
    private TextField fnameTF;

    @FXML
    private TableColumn<EmployeeModel, String> genderCol;

    @FXML
    private TextField genderTf;

    @FXML
    private TableColumn<EmployeeModel, String> lnameCol;

    @FXML
    private TextField lnameTf;

    @FXML
    private TableView<EmployeeModel> tableView;

    @FXML
    private JFXButton logoutBtn;
    @FXML
    private TableColumn<EmployeeModel, Integer> phoneCol;

    @FXML
    private TextField phoneNumberTf;

    @FXML
    private TextField salaryTF;

    @FXML
    private TableColumn<EmployeeModel, String> positionCol;

    @FXML
    private TextField PostionTF;

    @FXML
    private TextField searchTf;
    @FXML
    private TextField dateTf;
    private Parent root;
    private Stage stage;
    private Scene scene;

    private PreparedStatement prepare;
  

    @FXML
    void onClearHandler(ActionEvent event) {
        employeeIdTf.clear();
        fnameTF.clear();
        lnameTf.clear();
        phoneNumberTf.clear();
        PostionTF.clear();
        dateTf.clear();
    }

    private ObservableList<EmployeeModel> employee = FXCollections.observableArrayList();
    private DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection con = db.getConnection();
    private ResultSet resultSet;

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
    void onAddHandler(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to add?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if (option.get().equals(ButtonType.OK)) {

        String sql = "INSERT INTO payroll "
        + "(id,firstName,lastName,gross,overTime) "
        + "VALUES(?,?,?,?,?)";
    try {
        resultSet = con.createStatement().executeQuery("SELECT * FROM Employee_details WHERE id='" + employeeIdTf.getText().toString() + "'");

        if (resultSet.next()) {
            displayAlert(AlertType.ERROR, "Duplicate ID", "Please enter a unique ID.");
        } else {
            
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Employee_details VALUES(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, employeeIdTf.getText());
            preparedStatement.setString(2, fnameTF.getText());
            preparedStatement.setString(3, lnameTf.getText());
            preparedStatement.setString(4, (String) genderCB.getSelectionModel().getSelectedItem());
            preparedStatement.setString(5, phoneNumberTf.getText());
            preparedStatement.setString(6, PostionTF.getText());
            preparedStatement.setString(7, dateTf.getText());
            
            int result = preparedStatement.executeUpdate();

                    prepare = con.prepareStatement(sql);
                    prepare.setString(1, employeeIdTf.getText());
                    prepare.setString(2, fnameTF.getText());
                    prepare.setString(3, lnameTf.getText());
                    prepare.setString(4, "0.0");
                    prepare.setString(5, "0");
                    prepare.executeUpdate();
            
            tableView.getItems().clear(); 
            
            if (result == 1) {
                displayAlert(AlertType.INFORMATION, "Added Successfully", "New entry successfully added.");
            } else {
                displayAlert(AlertType.ERROR, "Entry Not Added", "Unable to add entry.");
            }

            getAllInfo();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        displayAlert(AlertType.ERROR, "Error", "Unable to add entry.");
    }
}
}

private String[] listGender = {"Male", "Female"};
    
    public void addEmployeeGenderList() {
        List<String> listG = new ArrayList<>();
        
        for (String data : listGender) {
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        genderCB.setItems(listData);
    }

    public void onDeleteHandler() {
        
        String sql = "Delete from Employee_details where id ='"
                + employeeIdTf.getText() + "'";

        
        con = db.getConnection();
        
        try {
            Alert alert;
            if (employeeIdTf.getText().isEmpty()
                    || fnameTF.getText().isEmpty()
                    || lnameTf.getText().isEmpty()
                    || genderCB.getSelectionModel().getSelectedItem() == null
                    || phoneNumberTf.getText().isEmpty()
                    || PostionTF.getText().isEmpty()
                    || dateTf.getText().isEmpty()
                    ) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank feilds");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete Employee ID: " + employeeIdTf.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    Statement statement = con.createStatement();
                    
                    statement.executeUpdate(sql);
                    
                    String deleteInfo = "DELETE from payroll where id ='"
                            + employeeIdTf.getText() + "'";
                    
                    prepare = con.prepareStatement(deleteInfo);

                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    tableView.getItems().clear();
                    getAllInfo();
                    tableView.setItems(employee);

                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

@FXML
void onUpdateHandler(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Message");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to update?");
    Optional<ButtonType> option = alert.showAndWait();
    
    if (option.get().equals(ButtonType.OK)) {

    try {
        int result = con.createStatement().executeUpdate

        ("update Employee_details set firstName=" + "'" + fnameTF.getText() + "'," +

                " lastName=" + "'" + lnameTf.getText() + "'," +

                " gender=" + "'" + genderCB.getSelectionModel().getSelectedItem() + "',"+

                " phone=" + "'" + phoneNumberTf.getText() + "',"+

                " venue =" + "'" + PostionTF.getText() + "',"+

                " date =" + "'" + dateTf.getText() + "'" + "where id= " + "'" + employeeIdTf.getText() + "'");
                
        if (result == 1) {
            displayAlert(AlertType.CONFIRMATION, "Updated", "Updated Succesfuly");
        } else {
            displayAlert(AlertType.ERROR, "Error", "Unable to Update");
        }
    } catch (Exception e) {

        e.printStackTrace();
    }

        try {
            //  int result = con.createStatement().executeUpdate
    
            // ("update payroll set firstName=" + "'" + fnameTF.getText() + "'," +
    
            //         " lastName=" + "'" + lnameTf.getText()+ "'");

    } catch (Exception e) {

        e.printStackTrace();
    }
    tableView.getItems().clear();
    

    getAllInfo();
}
}

@FXML
void onSearchHandler(ActionEvent event) {
    List<EmployeeModel> results = getEmployeeByLastName(searchTf.getText() + "%");

    if (results.size() > 0) {
        tableView.getItems().setAll(results);
        selectFirstEntry();
    } else {
        displayAlert(
            AlertType.INFORMATION,
            "First Name Not Found",
            "There are no entries with the specified last name");
    }
}

public void selectFirstEntry() {
    tableView.getSelectionModel().selectFirst();
}

    public void initialize() {
        addEmployeeGenderList();

        IdCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("firstName"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("lastName"));
        genderCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("gender"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, Integer>("phone"));
        positionCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("venue"));
        dateMemberCol.setCellValueFactory(new PropertyValueFactory<EmployeeModel, String>("date"));
        tableView.setItems(employee);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (ob, oldValue, newValue) -> {
                    displayEmployee(newValue);
                });
        getAllInfo();
    }

    private void getAllInfo() {
        try {
            resultSet = con.createStatement().executeQuery("select * from Employee_details");

            while (resultSet.next()) {

                employee.add(new EmployeeModel(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getInt("phone"),
                        resultSet.getString("venue"),
                        resultSet.getString("date")));
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
 
    
    public List<EmployeeModel> getEmployeeByLastName(String firstName) {
        List<EmployeeModel> results = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Employee_details WHERE firstName LIKE ? ORDER BY firstName, lastName";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "%" + firstName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                EmployeeModel employee = new EmployeeModel(
                    resultSet.getInt("id"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("gender"),
                    resultSet.getInt("phone"),
                    resultSet.getString("venue"),
                    resultSet.getString("date"));
                results.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
        


    public void displayEmployee(EmployeeModel eModel) {
        if (eModel != null) {
            employeeIdTf.setText(eModel.getId().toString());
            fnameTF.setText(eModel.getFirstName());
            lnameTf.setText(eModel.getLastName());
            // genderTf.setText(eModel.getGender());
            genderCB.setPromptText(eModel.getGender());
            phoneNumberTf.setText(eModel.getPhone().toString());
            PostionTF.setText(eModel.getVenue());
            dateTf.setText(eModel.getDate());
            // salaryTF.setText(eModel.getSalary().toString());

        } else {

            employeeIdTf.clear();
            fnameTF.clear();
            lnameTf.clear();
            phoneNumberTf.clear();
            PostionTF.clear();
            dateTf.clear();
        }
    }

}
