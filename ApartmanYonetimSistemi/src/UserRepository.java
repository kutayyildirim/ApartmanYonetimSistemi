import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection baglanti;

    public UserRepository() {
        this.baglanti = DatabaseConnection.getInstance().getConnection();
    }

    public User kullaniciGiris(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = baglanti.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String role = resultSet.getString("role");

            if ("admin".equalsIgnoreCase(role)) {
                return new Admin(id, username, role);
            } else if ("tenant".equalsIgnoreCase(role)) {
                return new Tenant(id, username, role);
            } else if ("owner".equalsIgnoreCase(role)) {
                return new Owner(id, username, role);
            }
        }

        return null;
    }
    // Kullanıcı ekleme metodu
    public boolean kullaniciEkle(User user, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = baglanti.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, password);
            stmt.setString(3, role);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kullanıcı başarıyla eklendi.");
                return true;
            } else {
                System.out.println("Kullanıcı eklenirken hata oluştu.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }

    // Kullanıcı silme metodu
    public boolean kullaniciSil(int userId, String role) {
        String query = "DELETE FROM users WHERE user_id = ? AND role = ?";
        try (PreparedStatement stmt = baglanti.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }


    // Kullanıcı Güncelle
    public boolean kullaniciGuncelle(int userId, String yeniAd, String yeniSifre, String yeniRol) {
        String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE user_id = ?";
        try (PreparedStatement stmt = baglanti.prepareStatement(query)) {
            stmt.setString(1, yeniAd);
            stmt.setString(2, yeniSifre);
            stmt.setString(3, yeniRol);
            stmt.setInt(4, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }
    public static int getUserIdByUsername(String username) {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static boolean kullaniciVarMi(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        String query = "SELECT username FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }



}
