import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class PayrollController implements Initializable {
    
    @FXML
    private Button btnDelete, btnSave, btnClear, btnReport;
    
    @FXML
    private TextField tx_name, tx_id, tx_gross, tx_basic, tx_house_rent, tx_medical,
                      tx_per_day, tx_per_hour, tx_over_time, tx_over_time_pay, tx_payable;
    @FXML
    private TableView<PayrollTable> table;
    
    @FXML
    private TableColumn<PayrollTable, String> tc_id, tc_name, tc_gross, tc_basic, tc_house_rent, tc_medical,
                                              tc_per_day, tc_per_hour, tc_over_time, tc_over_time_pay, tc_payable;
    
    ObservableList<PayrollTable> data = FXCollections.observableArrayList();
    private DatabaseConnectivty db = new DatabaseConnectivty();
    private Connection conn = db.getConnection();
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public void calculateValues() {
        
        double gross, over_time,Tax;
        
        if(tx_gross.getText().equals("") || tx_gross.getText().equals("0") || Double.parseDouble(tx_gross.getText()) < 0){
            gross = 0;
        }else{
            gross = Double.parseDouble(tx_gross.getText());
        }
        
        if(tx_over_time.getText().equals("") || Double.parseDouble(tx_over_time.getText()) < 0){
            over_time = 0;
        }else{
            over_time = Double.parseDouble(tx_over_time.getText());
        }
        
        double pension = (gross / 100) * 9; 
        pension = round(pension, 2);
        tx_house_rent.setText(String.valueOf(pension));

        if(gross>600 && gross<=1650){
            Tax = (gross / 100) * 10; 
        } else if(gross>1650 && gross<=3200){
            Tax = (gross / 100) * 15; 
        } else if(gross>3200 && gross<=5250){
            Tax = (gross / 100) * 20; 
        } else if(gross>5250 && gross<=7800){
            Tax = (gross / 100) * 25; 
        } else if(gross>7800 && gross<=10900){
            Tax = (gross / 100) * 30;
        } else if(gross>10900){
            Tax = (gross / 100) * 35;
        } else {
            Tax =0;
        }
        Tax = round(Tax, 2);
        tx_medical.setText(String.valueOf(Tax));
        
        double per_day = gross / 22; 
        per_day = round(per_day, 2);
        tx_per_day.setText(String.valueOf(per_day));
        
        double per_hour = per_day / 8;
        per_hour = round(per_hour, 2);
        tx_per_hour.setText(String.valueOf(per_hour));
        
        double over_time_pay = over_time * per_hour * 2;
        over_time_pay = round(over_time_pay, 2);
        tx_over_time_pay.setText(String.valueOf(over_time_pay));
        
        double payable = gross + over_time_pay - Tax - pension ;
        payable = round(payable, 2);
        tx_payable.setText(String.valueOf(payable));
    }
    
    @FXML
    public void Reload(){
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM payroll");
            data.clear();
            
            while(rs.next()){
                String id = rs.getString("id");
                String gross = rs.getString("gross");
                String overTime = rs.getString("overTime");
                PayrollTable pt = new PayrollTable(id, gross, overTime);
                data.add(pt);
            }
        }
        catch (SQLException ex){
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } 
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }
                stmt = null;
            }
        }

    }
    
    public void onTableClick() {
        tx_id.setText(table.getSelectionModel().getSelectedItem().getId());
        tx_gross.setText(table.getSelectionModel().getSelectedItem().getGross());
        tx_over_time.setText(table.getSelectionModel().getSelectedItem().getOverTime());
        calculateValues();
        
        btnDelete.setDisable(false);
        tx_id.setEditable(false);
    }
    
    @FXML
    public void onButtonClear() {
        tx_id.setText("");
        tx_gross.setText("");
        tx_over_time.setText("");
        calculateValues();
        
        btnDelete.setDisable(true);
        tx_id.setEditable(true);
    }
    
    @FXML
    public void onButtonDelete() {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            stmt.execute("DELETE FROM payroll WHERE id = " + tx_id.getText());
            stmt.execute("DELETE FROM Employee_details WHERE id = " + tx_id.getText());
        } catch (SQLException ex) {
            // ignore
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } 

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } 

                stmt = null;
            }
        }
        
        onButtonClear();
        Reload();
        btnDelete.setDisable(true);
        tx_id.setEditable(true);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_gross.setCellValueFactory(new PropertyValueFactory<>("gross"));
        tc_house_rent.setCellValueFactory(new PropertyValueFactory<>("house_rent"));
        tc_medical.setCellValueFactory(new PropertyValueFactory<>("medical"));
        tc_per_day.setCellValueFactory(new PropertyValueFactory<>("per_day"));
        tc_per_hour.setCellValueFactory(new PropertyValueFactory<>("per_hour"));
        tc_over_time.setCellValueFactory(new PropertyValueFactory<>("over_time"));
        tc_over_time_pay.setCellValueFactory(new PropertyValueFactory<>("over_time_pay"));
        tc_payable.setCellValueFactory(new PropertyValueFactory<>("payable"));
        table.setItems(data);
        
        Reload();
    }

}

