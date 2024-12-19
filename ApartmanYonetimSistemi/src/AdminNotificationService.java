import java.util.List;

public class AdminNotificationService {

    private NotificationManager manager;

    public AdminNotificationService() {
        this.manager = new NotificationManager();
    }

    public void bildirimGonder(String mesaj, List<String> kullaniciAdlari) {
        for (String kullaniciAdi : kullaniciAdlari) {
            if (UserRepository.kullaniciVarMi(kullaniciAdi)) { // Kullanıcı doğrulama
                if (!isUserAlreadySubscribed(kullaniciAdi)) {
                    manager.aboneEkle(new UserNotification(kullaniciAdi));
                }
            } else {
                System.err.println("Geçersiz kullanıcı adı: " + kullaniciAdi);
            }
        }

        // Bildirimi gönder
        manager.bildirimGonder(mesaj);
    }
    private boolean isUserAlreadySubscribed(String kullaniciAdi) {

        return false;
    }
}
