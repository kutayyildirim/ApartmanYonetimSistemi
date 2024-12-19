import java.util.ArrayList;
import java.util.List;

public interface Subject {
    void aboneEkle(Observer observer);
    void aboneCikar(Observer observer);
    void bildirimGonder(String mesaj);
}
