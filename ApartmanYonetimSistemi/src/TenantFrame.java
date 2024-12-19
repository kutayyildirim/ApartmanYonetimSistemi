import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class TenantFrame extends JFrame {

    private JButton daireGoruntuleBtn;
    private JButton dairedenCikBtn;
    private int userId; // Kullanıcının ID'si
    private JList<String> notificationList;
    private DefaultListModel<String> listModel;

    private int tenantId; // Mevcut kiracının ID'si
    private ApartmentRepository apartmentRepository = new ApartmentRepository();

    public TenantFrame(int tenantId) {
        this.tenantId = tenantId;
        setTitle("Tenant Paneli");
        setSize(400, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Daire görüntüleme butonu
        daireGoruntuleBtn = new JButton("Dairemi Görüntüle");
        daireGoruntuleBtn.setBounds(50, 50, 200, 30);
        add(daireGoruntuleBtn);

        // Daireden çıkma butonu
        dairedenCikBtn = new JButton("Daireden Çık");
        dairedenCikBtn.setBounds(50, 100, 200, 30);
        add(dairedenCikBtn);

        // Daire görüntüleme event'i
        daireGoruntuleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Apartment daire = apartmentRepository.kiraciApartmaniniGetir(tenantId);
                    if (daire != null) {
                        String mesaj = "Daire ID: " + daire.getApartmentId() +
                                "\nBlok: " + daire.getBlockName() +
                                "\nTip: " + daire.getType() +
                                "\nDurum: " + daire.getStatus() +
                                "\nEşyalı: " + (daire.isFurnished() ? "Evet" : "Hayır");
                        JOptionPane.showMessageDialog(null, mesaj, "Daire Bilgisi", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Herhangi bir dairede oturmuyorsunuz.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Daire bilgisi getirilirken hata oluştu: " + ex.getMessage());
                }
            }
        });

        // Daireden çıkma event'i
        dairedenCikBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean sonuc = apartmentRepository.kiraciApartmaniniBosalt(tenantId);
                    if (sonuc) {
                        JOptionPane.showMessageDialog(null, "Daireden başarıyla çıktınız.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Daireden çıkarken bir hata oluştu veya daire bulunamadı.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                }
            }
        });


        setVisible(true);
    }
    public TenantFrame(String username) {
        this.userId = UserRepository.getUserIdByUsername(username);

        setTitle("Tenant - Bildirimler");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bildirim Listesi
        listModel = new DefaultListModel<>();
        notificationList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(notificationList);

        // Butonlar
        JButton refreshButton = new JButton("Bildirimleri Yenile");
        JButton markAsReadButton = new JButton("Okundu Olarak İşaretle");

        refreshButton.addActionListener(e -> bildirimleriYukle());
        markAsReadButton.addActionListener(e -> bildirimleriOkunduOlarakIsaretle());

        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(markAsReadButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // İlk yükleme
        bildirimleriYukle();
    }

    private void bildirimleriYukle() {
        listModel.clear();
        List<String> notifications = NotificationRepository.getNotificationsByUserId(userId);
        for (String notification : notifications) {
            listModel.addElement(notification);
        }
    }

    private void bildirimleriOkunduOlarakIsaretle() {
        NotificationRepository.markNotificationsAsRead(userId);
        JOptionPane.showMessageDialog(this, "Tüm bildirimler okundu olarak işaretlendi.");
        bildirimleriYukle();
    }



}