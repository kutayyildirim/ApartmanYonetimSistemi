import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    private static final String STATUS_UNREAD = "UNREAD";
    private static final String STATUS_READ = "READ";

    public static List<String> getNotificationsByUserId(int userId) {
        List<String> notifications = new ArrayList<>();
        String query = "SELECT message FROM notifications WHERE user_id = ? AND status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, STATUS_UNREAD);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notifications.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public static void markNotificationsAsRead(int userId) {
        String query = "UPDATE notifications SET status = ? WHERE user_id = ? AND status = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, STATUS_READ);
            stmt.setInt(2, userId);
            stmt.setString(3, STATUS_UNREAD);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

