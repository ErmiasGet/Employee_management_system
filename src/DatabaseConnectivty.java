import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectivty {
    private final String url = "jdbc:mysql://localhost/employeemanagement";
    private final String username = "root";
    private final String password = "root";
    private Connection connection;

    DatabaseConnectivty() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

}
