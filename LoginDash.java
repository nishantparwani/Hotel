import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Unit 6: JFrame container, Layout, Event Handling
// Unit 3: Class, constructor, objects
class Login extends JFrame {

    // Unit 3: instance variables
    JTextField usernameField;
    JPasswordField passwordField;

    // Unit 1: constants (final keyword from Unit 4)
    final String CORRECT_USER = "admin";
    final String CORRECT_PASS = "hotel123";

    Login() {
        setTitle("MPSTME Hotel");
        setSize(380, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(20, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(6, 40, 6, 40);

        JLabel titleLabel = new JLabel("MPSTME HOTEL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setForeground(new Color(212, 175, 95));
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 40, 4, 40);
        panel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Hotel Management System", SwingConstants.CENTER);
        subLabel.setFont(new Font("Serif", Font.ITALIC, 13));
        subLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 40, 2, 40);
        panel.add(subLabel, gbc);

        JLabel authorLabel = new JLabel("By Nishant Parwani", SwingConstants.CENTER);
        authorLabel.setFont(new Font("Serif", Font.ITALIC, 11));
        authorLabel.setForeground(new Color(140, 110, 55));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 40, 20, 40);
        panel.add(authorLabel, gbc);

        gbc.insets = new Insets(4, 40, 4, 40);

        gbc.gridy = 3;
        panel.add(makeLabel("Username"), gbc);

        usernameField = makeTextField();
        usernameField.setText("admin");
        gbc.gridy = 4;
        panel.add(usernameField, gbc);

        gbc.gridy = 5;
        panel.add(makeLabel("Password"), gbc);

        passwordField = new JPasswordField("hotel123");
        styleField(passwordField);
        gbc.gridy = 6;
        panel.add(passwordField, gbc);

        JButton loginButton = makeButton("LOGIN");
        gbc.gridy = 7;
        gbc.insets = new Insets(18, 40, 6, 40);
        panel.add(loginButton, gbc);

        JLabel hintLabel = new JLabel("admin  /  hotel123", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Monospaced", Font.PLAIN, 10));
        hintLabel.setForeground(Color.GRAY);
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 40, 20, 40);
        panel.add(hintLabel, gbc);

        setContentPane(panel);

        // Unit 6: Event Handling using ActionListener
        ActionListener loginAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        };

        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
    }

    // Unit 3: method inside class
    void handleLogin() {
        String enteredUser = usernameField.getText();
        String enteredPass = new String(passwordField.getPassword());

        // Unit 2: if-else selection statement
        if (enteredUser.equals(CORRECT_USER) && enteredPass.equals(CORRECT_PASS)) {
            dispose();
            new Dashboard().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! Use admin / hotel123", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.PLAIN, 13));
        label.setForeground(Color.GRAY);
        return label;
    }

    JTextField makeTextField() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    void styleField(JTextField field) {
        field.setBackground(new Color(35, 35, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 58, 50)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
    }

    JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(212, 175, 95));
        button.setForeground(new Color(20, 20, 30));
        button.setFont(new Font("Georgia", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}

// Unit 6: JFrame container, BorderLayout, Event Handling
class Dashboard extends JFrame {

    JPanel contentArea;

    Dashboard() {
        setTitle("MPSTME Hotel - Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 30));

        add(makeSidebar(), BorderLayout.WEST);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(20, 20, 30));
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentArea, BorderLayout.CENTER);

        showPanel(new HomePanel());
    }

    JPanel makeSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(24, 24, 34));
        sidebar.setPreferredSize(new Dimension(190, 650));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(50, 48, 40)));

        JLabel hotelName = new JLabel("MPSTME HOTEL", SwingConstants.CENTER);
        hotelName.setFont(new Font("Georgia", Font.BOLD, 13));
        hotelName.setForeground(new Color(212, 175, 95));
        hotelName.setAlignmentX(Component.CENTER_ALIGNMENT);
        hotelName.setBorder(BorderFactory.createEmptyBorder(22, 0, 4, 0));
        sidebar.add(hotelName);

        JLabel credit = new JLabel("Nishant Parwani", SwingConstants.CENTER);
        credit.setFont(new Font("Serif", Font.ITALIC, 10));
        credit.setForeground(new Color(140, 110, 55));
        credit.setAlignmentX(Component.CENTER_ALIGNMENT);
        credit.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        sidebar.add(credit);

        // Unit 1: Array (String array)
        // Unit 2: for loop iterating over array
        String[] navItems = {"Dashboard", "Check In", "Check Out", "Rooms", "Bookings", "Feedback", "Admin"};
        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            JButton navBtn = makeNavButton(item);
            navBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    navigateTo(item);
                }
            });
            sidebar.add(navBtn);
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = makeNavButton("Logout");
        logoutBtn.setForeground(new Color(200, 70, 70));
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION);
                // Unit 2: if selection statement
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });

        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(12));
        return sidebar;
    }

    JButton makeNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.PLAIN, 13));
        button.setForeground(Color.GRAY);
        button.setBackground(new Color(24, 24, 34));
        button.setMaximumSize(new Dimension(190, 38));
        button.setPreferredSize(new Dimension(190, 38));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Unit 2: if-else if ladder (selection statements)
    void navigateTo(String section) {
        JPanel panel;
        if (section.equals("Check In")) {
            panel = new CheckInPanel();
        } else if (section.equals("Check Out")) {
            panel = new CheckOutPanel();
        } else if (section.equals("Rooms")) {
            panel = new RoomsPanel();
        } else if (section.equals("Bookings")) {
            panel = new BookingsPanel();
        } else if (section.equals("Feedback")) {
            panel = new FeedbackPanel();
        } else if (section.equals("Admin")) {
            panel = new AdminPanel();
        } else {
            panel = new HomePanel();
        }
        showPanel(panel);
    }

    void showPanel(JPanel panel) {
        contentArea.removeAll();
        contentArea.add(panel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }
}
