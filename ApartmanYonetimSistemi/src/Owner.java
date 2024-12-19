import java.sql.SQLException;

public class Owner extends User {

    public Owner(int id, String username, String role) {
        super(id, username, role);
    }

    // Kendi apartmanını görüntüleme
    public void apartmanBilgileriniGoruntule() throws SQLException {
        ApartmentRepository apartmentRepository = new ApartmentRepository();
        Apartment apartman = apartmentRepository.sahipApartmaniniGetir(this.getId());

        if (apartman != null) {
            System.out.println("Apartman ID: " + apartman.getApartmentId());
            System.out.println("Blok: " + apartman.getBlockName());
            System.out.println("Tip: " + apartman.getType());
            System.out.println("Durum: " + apartman.getStatus());
            System.out.println("Eşyalı mı: " + (apartman.isFurnished() ? "Evet" : "Hayır"));
        } else {
            System.out.println("Herhangi bir apartmanınız görünmüyor.");
        }
    }

    // Kendi apartmanını satma (Durumunu "Sold" yapma)
    public void apartmanSat() throws SQLException {
        ApartmentRepository apartmentRepository = new ApartmentRepository();
        boolean basarili = apartmentRepository.sahipApartmaniniSat(this.getId());

        if (basarili) {
            System.out.println("Apartman başarıyla satıldı.");
        } else {
            System.out.println("Apartman satılırken bir hata oluştu.");
        }
    }
}
