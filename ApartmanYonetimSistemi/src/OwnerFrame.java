import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class OwnerFrame extends JFrame {

    private JButton daireGoruntuleBtn;
    private JButton daireSatBtn;
    private int userId;
    private JList<String> notificationList;
    private DefaultListModel<String> listModel;
    private int ownerId;

    private ApartmentRepository apartmentRepository = new ApartmentRepository();

    public OwnerFrame(int ownerId) {
        this.ownerId = ownerId;
        setTitle("Owner Paneli");
        setSize(400, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Daire görüntüleme butonu
        daireGoruntuleBtn = new JButton("Dairemi Görüntüle");
        daireGoruntuleBtn.setBounds(50, 50, 200, 30);
        add(daireGoruntuleBtn);

        // Daire satma butonu
        daireSatBtn = new JButton("Dairemi Sat");
        daireSatBtn.setBounds(50, 100, 200, 30);
        add(daireSatBtn);

        // Daire görüntüleme event'i
        daireGoruntuleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Apartment apartman = apartmentRepository.sahipApartmaniniGetir(ownerId);
                    if (apartman != null) {
                        String mesaj = "Daire ID: " + apartman.getApartmentId() +
                                "\nBlok: " + apartman.getBlockName() +
                                "\nTip: " + apartman.getType() +
                                "\nDurum: " + apartman.getStatus() +
                                "\nEşyalı: " + (apartman.isFurnished() ? "Evet" : "Hayır");
                        JOptionPane.showMessageDialog(null, mesaj, "Daire Bilgisi", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Herhangi bir daireye sahip değilsiniz.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Daire bilgisi getirilirken hata oluştu: " + ex.getMessage());
                }
            }
        });

        // Daire satma event'i
        daireSatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean sonuc = apartmentRepository.sahipApartmaniniSat(ownerId);
                    if (sonuc) {
                        JOptionPane.showMessageDialog(null, "Daire başarıyla satıldı.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Daire satılırken bir hata oluştu veya daire bulunamadı.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
    public OwnerFrame(String username) {
        this.userId = UserRepository.getUserIdByUsername(username);

        setTitle("Owner - Bildirimler");
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