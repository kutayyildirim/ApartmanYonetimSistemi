import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public MainFrame() {
        // Pencere Başlığı
        setTitle("Apartman Yönetim Sistemi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel ve Bileşenler
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Giriş Yap");
        loginButton.addActionListener(new LoginActionListener());


        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);


        add(panel);
    }

    // Giriş İşlemi
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                // Kullanıcı giriş kontrolü
                UserRepository userRepository = new UserRepository();
                User user = userRepository.kullaniciGiris(username, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(null, "Giriş Başarılı! Rol: " + user.getRole());
                    openRoleSpecificFrame(user);
                } else {
                    JOptionPane.showMessageDialog(null, "Hatalı kullanıcı adı veya şifre.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + ex.getMessage());
            }
        }
    }

    // Rol Bazlı Arayüz
    private void openRoleSpecificFrame(User user) {
        if ("admin".equalsIgnoreCase(user.getRole())) {
            new AdminFrame(user).setVisible(true);  // AdminFrame
        } else if ("tenant".equalsIgnoreCase(user.getRole())) {
            new TenantFrame(user.getId()).setVisible(true);  // TenantFrame
        } else if ("owner".equalsIgnoreCase(user.getRole())) {
            new OwnerFrame(user.getId()).setVisible(true);  // OwnerFrame
        }
        dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
