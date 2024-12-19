import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminFrame extends JFrame {

    private JButton daireListeleBtn;
    private JButton kullaniciEkleBtn;
    private JButton kullaniciSilBtn;
    private JButton kullaniciGuncelleBtn;
    private JButton apartmanDurumuGuncelleBtn;
    private JButton bildirimGonderBtn;
    private JTextArea mesajArea;
    private JList<String> kullaniciListesi;

    private UserRepository userRepository = new UserRepository();
    private ApartmentRepository apartmentRepository = new ApartmentRepository();

    public AdminFrame(User user) {
        setTitle("Admin Paneli");
        setSize(400, 350);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Kullanıcı ekle butonu
        kullaniciEkleBtn = new JButton("Kullanıcı Ekle");
        kullaniciEkleBtn.setBounds(50, 70, 150, 30);
        add(kullaniciEkleBtn);

        // Kullanıcı sil butonu
        kullaniciSilBtn = new JButton("Kullanıcı Sil");
        kullaniciSilBtn.setBounds(50, 110, 150, 30);
        add(kullaniciSilBtn);

        // Kullanıcı güncelle butonu
        kullaniciGuncelleBtn = new JButton("Kullanıcı Güncelle");
        kullaniciGuncelleBtn.setBounds(50, 150, 150, 30);
        add(kullaniciGuncelleBtn);

        // Daireleri listele butonu
        daireListeleBtn = new JButton("Daireleri Listele");
        daireListeleBtn.setBounds(50, 190, 150, 30);
        add(daireListeleBtn);

        // Apartman durumu güncelleme butonu
        apartmanDurumuGuncelleBtn = new JButton("Apartman Durumunu Güncelle");
        apartmanDurumuGuncelleBtn.setBounds(50, 230, 250, 30); // Boyutlandırma
        add(apartmanDurumuGuncelleBtn);

        // Bildirim gönderme butonu
        bildirimGonderBtn = new JButton("Bildirim Gönder");
        bildirimGonderBtn.setBounds(50, 270, 150, 30); // Yeni buton
        add(bildirimGonderBtn);

        // Kullanıcıları listele
        kullaniciListesi = new JList<>();
        JScrollPane scrollPane = new JScrollPane(kullaniciListesi);
        scrollPane.setBounds(50, 310, 150, 100);
        add(scrollPane);

        // Mesaj girişi için TextArea
        mesajArea = new JTextArea();
        JScrollPane mesajScroll = new JScrollPane(mesajArea);
        mesajScroll.setBounds(220, 310, 200, 100);
        add(mesajScroll);

        // Kullanıcı ekleme event'i
        kullaniciEkleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = JOptionPane.showInputDialog("Kullanıcı Adı:");
                    if (username == null || username.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Kullanıcı adı boş olamaz!");
                        return;
                    }
                    String password = JOptionPane.showInputDialog("Şifre:");
                    if (password == null || password.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Şifre boş olamaz!");
                        return;
                    }
                    String role = JOptionPane.showInputDialog("Rol (admin, tenant, owner):");
                    if (role == null || (!role.equalsIgnoreCase("admin") &&
                            !role.equalsIgnoreCase("tenant") &&
                            !role.equalsIgnoreCase("owner"))) {
                        JOptionPane.showMessageDialog(null, "Geçerli bir rol giriniz (admin, tenant, owner)!");
                        return;
                    }

                    User user;
                    switch (role.toLowerCase()) {
                        case "admin":
                            user = new Admin(0, username, role);
                            break;
                        case "tenant":
                            user = new Tenant(0, username, role);
                            break;
                        case "owner":
                            user = new Owner(0, username, role);
                            break;
                        default:
                            throw new IllegalArgumentException("Geçersiz rol: " + role);
                    }

                    boolean sonuc = userRepository.kullaniciEkle(user, password, role);
                    if (sonuc) {
                        JOptionPane.showMessageDialog(null, "Kullanıcı başarıyla eklendi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Kullanıcı eklenirken hata oluştu!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
                }
            }
        });

        // Kullanıcı silme event'i
        kullaniciSilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String userIdStr = JOptionPane.showInputDialog("Silinecek Kullanıcı ID:");
                    int userId = Integer.parseInt(userIdStr);

                    String role = JOptionPane.showInputDialog("Kullanıcının Rolü (admin, tenant, owner):");
                    if (role == null || (!role.equalsIgnoreCase("admin") &&
                            !role.equalsIgnoreCase("tenant") &&
                            !role.equalsIgnoreCase("owner"))) {
                        JOptionPane.showMessageDialog(null, "Geçerli bir rol giriniz (admin, tenant, owner)!");
                        return;
                    }

                    boolean sonuc = userRepository.kullaniciSil(userId, role);
                    if (sonuc) {
                        JOptionPane.showMessageDialog(null, "Kullanıcı başarıyla silindi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Kullanıcı silinirken hata oluştu!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
                }
            }
        });

        // Kullanıcı güncelleme event'i
        kullaniciGuncelleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String userIdStr = JOptionPane.showInputDialog("Güncellenecek Kullanıcı ID:");
                    int userId = Integer.parseInt(userIdStr);

                    String yeniAd = JOptionPane.showInputDialog("Yeni Kullanıcı Adı:");
                    if (yeniAd == null || yeniAd.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Kullanıcı adı boş olamaz!");
                        return;
                    }

                    String yeniSifre = JOptionPane.showInputDialog("Yeni Şifre:");
                    if (yeniSifre == null || yeniSifre.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Şifre boş olamaz!");
                        return;
                    }

                    String yeniRol = JOptionPane.showInputDialog("Yeni Rol:");
                    if (yeniRol == null || (!yeniRol.equalsIgnoreCase("admin") &&
                            !yeniRol.equalsIgnoreCase("tenant") &&
                            !yeniRol.equalsIgnoreCase("owner"))) {
                        JOptionPane.showMessageDialog(null, "Geçerli bir rol giriniz (admin, tenant, owner)!");
                        return;
                    }

                    boolean sonuc = userRepository.kullaniciGuncelle(userId, yeniAd, yeniSifre, yeniRol);
                    if (sonuc) {
                        JOptionPane.showMessageDialog(null, "Kullanıcı başarıyla güncellendi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Kullanıcı güncellenirken hata oluştu!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
                }
            }
        });

        // Daire listeleme event'i
        daireListeleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Örneğin: JTable ile tüm daireleri listele
                JFrame frame = new JFrame("Daire Listesi");
                String[] columnNames = {"ID", "Blok", "Tip", "Durum"};
                Object[][] data = apartmentRepository.getApartmentsAsTableData();

                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);
                frame.setSize(500, 300);
                frame.setVisible(true);
            }
        });

        // Apartman durumu güncelleme event'i
        apartmanDurumuGuncelleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String apartmentIdStr = JOptionPane.showInputDialog("Apartman ID:");
                    int apartmentId = Integer.parseInt(apartmentIdStr);

                    String yeniDurum = JOptionPane.showInputDialog("Yeni Durum (Empty, Sold, Rented):");

                    // Tenant ve Owner ID'leri almak
                    Integer tenantId = null;
                    Integer ownerId = null;

                    if ("Rented".equalsIgnoreCase(yeniDurum)) {
                        String tenantIdStr = JOptionPane.showInputDialog("Kiracı ID:");
                        tenantId = Integer.parseInt(tenantIdStr);
                    } else if ("Sold".equalsIgnoreCase(yeniDurum)) {
                        String ownerIdStr = JOptionPane.showInputDialog("Ev Sahibi ID:");
                        ownerId = Integer.parseInt(ownerIdStr);
                    }

                    Admin admin = (Admin) user;  // Admin'i cast et
                    boolean sonuc = apartmentRepository.apartmanDurumGuncelle(apartmentId, yeniDurum, tenantId, ownerId);
                    if (sonuc) {
                        JOptionPane.showMessageDialog(null, "Durum başarıyla güncellendi!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Durum güncellenirken hata oluştu!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
                }
            }
        });
        bildirimGonderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kullanıcı listesini dinamik olarak yükle
                    UserRepository userRepository = new UserRepository();
                    List<String> allUsernames = userRepository.getAllUsernames();

                    // Eğer sistemde kullanıcı yoksa uyarı ver
                    if (allUsernames.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Sistemde kayıtlı kullanıcı bulunmamaktadır!");
                        return;
                    }

                    // Kullanıcı seçimi için dialog göster
                    String[] userArray = allUsernames.toArray(new String[0]);
                    JList<String> userSelectionList = new JList<>(userArray);
                    userSelectionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                    int userSelectResult = JOptionPane.showConfirmDialog(
                            null,
                            new JScrollPane(userSelectionList),
                            "Kullanıcı Seç",
                            JOptionPane.OK_CANCEL_OPTION
                    );

                    if (userSelectResult != JOptionPane.OK_OPTION) {
                        return; // İşlem iptal edildiyse çık
                    }

                    // Seçilen kullanıcıları al
                    List<String> selectedUsers = userSelectionList.getSelectedValuesList();
                    if (selectedUsers.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Lütfen en az bir kullanıcı seçiniz!");
                        return;
                    }

                    // Mesaj yazma ekranı göster
                    JTextArea mesajArea = new JTextArea(5, 20);
                    int mesajResult = JOptionPane.showConfirmDialog(
                            null,
                            new JScrollPane(mesajArea),
                            "Mesajınızı Yazın",
                            JOptionPane.OK_CANCEL_OPTION
                    );

                    if (mesajResult != JOptionPane.OK_OPTION) {
                        return; // İşlem iptal edildiyse çık
                    }

                    // Mesajı al
                    String mesaj = mesajArea.getText().trim();
                    if (mesaj.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Lütfen bir mesaj giriniz!");
                        return;
                    }

                    // AdminNotificationService ile bildirim gönder
                    AdminNotificationService notificationService = new AdminNotificationService();
                    notificationService.bildirimGonder(mesaj, selectedUsers);

                    // Başarı mesajı
                    JOptionPane.showMessageDialog(null, "Bildirim başarıyla gönderildi!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
                }
            }
        });









        setVisible(true);

    }

}
