import java.sql.SQLException;

public class Admin extends User {

    public Admin(int id, String username, String role) {
        super(id, username, role);
    }

    // Yeni kullanıcı ekleme
    public void kullaniciEkle(String username, String password, String role) throws SQLException {
        UserRepository userRepository = new UserRepository();
        UserFactory factory;

        // Rolüne göre doğru factory seç
        if ("admin".equalsIgnoreCase(role)) {
            factory = new AdminFactory();
        } else if ("tenant".equalsIgnoreCase(role)) {
            factory = new TenantFactory();
        } else if ("owner".equalsIgnoreCase(role)) {
            factory = new OwnerFactory();
        } else {
            System.out.println("Hatalı rol seçimi!");
            return;
        }

        // Yeni kullanıcı oluştur ve veritabanına ekle
        User yeniKullanici = factory.createUser(username);
        boolean basarili = userRepository.kullaniciEkle(yeniKullanici, password, role);

        if (basarili) {
            System.out.println("Kullanıcı başarıyla eklendi.");
        } else {
            System.out.println("Kullanıcı eklenirken bir hata oluştu.");
        }
    }

    // Apartman durumu güncelleme
    public void apartmanDurumuGuncelle(int apartmentId, String yeniDurum, Integer tenantId, Integer ownerId) throws SQLException {
        ApartmentRepository apartmentRepository = new ApartmentRepository();

        boolean guncellemeBasarili = apartmentRepository.apartmanDurumGuncelle(apartmentId, yeniDurum, tenantId, ownerId);

        if (guncellemeBasarili) {
            System.out.println("Apartman durumu başarıyla güncellendi.");
        } else {
            System.out.println("Apartman durumu güncellenirken bir hata oluştu.");
        }
    }
}
