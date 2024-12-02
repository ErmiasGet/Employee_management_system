import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PayrollTable {

    private final SimpleStringProperty tc_id;
    private final SimpleStringProperty tc_gross;
    private final SimpleStringProperty tc_over_time;
    private final SimpleStringProperty tc_house_rent;
    private final SimpleStringProperty tc_medical;
    private final SimpleStringProperty tc_per_day;
    private final SimpleStringProperty tc_per_hour;
    private final SimpleStringProperty tc_over_time_pay;
    private final SimpleStringProperty tc_payable;

    PayrollTable(String id, String gross, String overtime) {
        this.tc_id = new SimpleStringProperty(id);
        this.tc_gross = new SimpleStringProperty(gross);
        this.tc_over_time = new SimpleStringProperty(overtime);

        double Tax;
        if(Double.parseDouble(gross)>600 && Double.parseDouble(gross)<=1650){
            Tax = (Double.parseDouble(gross) / 100) * 10; 
        } else if(Double.parseDouble(gross)>1650 && Double.parseDouble(gross)<=3200){
            Tax = (Double.parseDouble(gross) / 100) * 15; 
        } else if(Double.parseDouble(gross)>3200 && Double.parseDouble(gross)<=5250){
            Tax = (Double.parseDouble(gross) / 100) * 20; 
        } else if(Double.parseDouble(gross)>5250 && Double.parseDouble(gross)<=7800){
            Tax = (Double.parseDouble(gross) / 100) * 25; 
        } else if(Double.parseDouble(gross)>7800 && Double.parseDouble(gross)<=10900){
            Tax = (Double.parseDouble(gross) / 100) * 30;
        } else if(Double.parseDouble(gross)>10900){
            Tax = (Double.parseDouble(gross) / 100) * 35;
        } else {
            Tax =0;
        }

        Tax = round(Tax, 2);
        this.tc_medical = new SimpleStringProperty(String.valueOf(Tax));

        double pension = (Double.parseDouble(gross) / 100) * 9; 
        pension = round(pension, 2);
        this.tc_house_rent = new SimpleStringProperty(String.valueOf(pension));
   

        double per_day = (Double.parseDouble(gross) / 22); 
        per_day = round(per_day, 2);
        this.tc_per_day = new SimpleStringProperty(String.valueOf(per_day));

        double per_hour = per_day / 8; 
        per_hour = round(per_hour, 2);
        this.tc_per_hour = new SimpleStringProperty(String.valueOf(per_hour));

        double over_time_pay = Double.parseDouble(overtime) * per_hour * 2; // Over Time Pay = Over Time * Per Hour Pay * 2
        over_time_pay = round(over_time_pay, 2);
        this.tc_over_time_pay = new SimpleStringProperty(String.valueOf(over_time_pay));

        double payable = (Double.parseDouble(gross)) + over_time_pay - Tax - pension; // Payable = Basic + Over Time Pay
        payable = round(payable, 2);
        this.tc_payable = new SimpleStringProperty(String.valueOf(payable));
    }

    public final String getId() { return tc_id.get(); }
    public final String getGross() { return tc_gross.get(); }
    public final String getOverTime() { return tc_over_time.get(); }
    public final StringProperty idProperty() { return tc_id; }
    public final StringProperty grossProperty() { return tc_gross; }
    public final StringProperty house_rentProperty() { return tc_house_rent; }
    public final StringProperty medicalProperty() { return tc_medical; }
    public final StringProperty per_dayProperty() { return tc_per_day; }
    public final StringProperty per_hourProperty() { return tc_per_hour; }
    public final StringProperty over_timeProperty() { return tc_over_time; }
    public final StringProperty over_time_payProperty() { return tc_over_time_pay; }
    public final StringProperty payableProperty() { return tc_payable; }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
