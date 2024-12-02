public class EmployeeModel {
    Integer id;
    String firstName;
    String lastName;
    String gender;
    Integer phone;
    String venue;
    String date;
    Double gross;
    Integer overTime;

    public EmployeeModel(Integer id, String firstName, String lastName, String gender,Integer phone, String venue,String date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone=phone;
        this.gender = gender;
        this.id = id;
        this.venue=venue;
        this.date = date;
    }
    public EmployeeModel(Integer id, String firstName, String lastName,Double gross, Integer overTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.gross = gross;
        this.overTime = overTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
