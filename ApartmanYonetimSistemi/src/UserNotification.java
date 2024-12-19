public class UserNotification implements Observer {

    private String kullaniciAdi;
    private String status;  // Okunma durumu (Unread / Read)

    public UserNotification(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
        this.status = "UNREAD";  // Başlangıçta "UNREAD"
    }

    @Override
    public void bildirimAl(String mesaj) {
        System.out.println(kullaniciAdi + " için bildirim: " + mesaj);
        System.out.println("Durum: " + status);
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    // Okundu olarak işaretle
    public void markAsRead() {
        this.status = "READ";  // Durumu "READ" olarak değiştir
    }

    // Durum bilgisini al
    public String getStatus() {
        return status;
    }
}
