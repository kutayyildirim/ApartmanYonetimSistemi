import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements Subject {

    private List<Observer> aboneler;

    public NotificationManager() {
        this.aboneler = new ArrayList<>();
    }

    @Override
    public void aboneEkle(Observer observer) {
        aboneler.add(observer);
    }

    @Override
    public void aboneCikar(Observer observer) {
        aboneler.remove(observer);
    }

    @Override
    public void bildirimGonder(String mesaj) {
        for (Observer abone : aboneler) {
            abone.bildirimAl(mesaj);
            kaydetBildirimVeritabanina(((UserNotification) abone).getKullaniciAdi(), mesaj);
        }
    }

    private void kaydetBildirimVeritabanina(String kullaniciAdi, String mesaj) {
        String query = "INSERT INTO notifications (user_id, message, is_read, created_at) VALUES (?, ?, 0, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            int userId = UserRepository.getUserIdByUsername(kullaniciAdi);
            stmt.setInt(1, userId);
            stmt.setString(2, mesaj);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
