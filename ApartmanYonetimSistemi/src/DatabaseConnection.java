import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/apartmentmanagement";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "345kty";

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            System.err.println("Bağlantı kapatılırken hata oluştu: " + e.getMessage());
        }
    }
}
