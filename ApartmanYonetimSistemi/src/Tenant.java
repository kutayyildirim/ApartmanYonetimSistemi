import java.sql.SQLException;

public class Tenant extends User {

    public Tenant(int id, String username, String role) {
        super(id, username, role);
    }
    public int getUserId() {
        return getId();
    }


    // Kendi apartmanını görüntüleme
    public void apartmanBilgileriniGoruntule() throws SQLException {
        ApartmentRepository apartmentRepository = new ApartmentRepository();
        Apartment apartman = apartmentRepository.kiraciApartmaniniGetir(this.getId());

        if (apartman != null) {
            System.out.println("Apartman ID: " + apartman.getApartmentId());
            System.out.println("Blok: " + apartman.getBlockName());
            System.out.println("Tip: " + apartman.getType());
            System.out.println("Durum: " + apartman.getStatus());
            System.out.println("Eşyalı mı: " + (apartman.isFurnished() ? "Evet" : "Hayır"));
        } else {
            System.out.println("Herhangi bir apartmana atanmış görünmüyorsunuz.");
        }
    }

    // Kendi apartmanını boşaltma
    public void apartmanBosalt() throws SQLException {
        ApartmentRepository apartmentRepository = new ApartmentRepository();
        boolean basarili = apartmentRepository.kiraciApartmaniniBosalt(this.getId());

        if (basarili) {
            System.out.println("Apartman başarıyla boşaltıldı.");
        } else {
            System.out.println("Apartman boşaltılırken bir hata oluştu.");
        }
    }
}
