package com.library.lib.gui;

import com.library.lib.model.LibraryUser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LibraryDashboard extends JFrame {

    private LibraryUser loggedInUser;

    public LibraryDashboard(LibraryUser user) {
        System.out.println("LibraryDashboard: Constructor called.");
        if (user == null) {
            System.out.println("LibraryDashboard: Warning - loggedInUser is null. Creating a guest session.");
            this.loggedInUser = new LibraryUser("guest", "guestpass", "Guest User", "guest");
        } else {
            this.loggedInUser = user;
            System.out.println("LibraryDashboard: User received: " + this.loggedInUser.getUsername());
        }

        setTitle("Library Dashboard - Welcome, " + this.loggedInUser.getUsername());
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(0, 102, 102);
                Color color2 = new Color(0, 153, 153);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(20, 20));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome to Library Dashboard!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        northPanel.add(welcomeLabel);
        backgroundPanel.add(northPanel, BorderLayout.NORTH);

        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setOpaque(false);
        mainButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton bookManagementButton = createStyledButton("Book Management");
        mainButtonPanel.add(bookManagementButton, gbc);

        gbc.gridy++;
        JButton memberManagementButton = createStyledButton("Member Management");
        mainButtonPanel.add(memberManagementButton, gbc);

        gbc.gridy++;
        JButton borrowingManagementButton = createStyledButton("Borrowing Management");
        mainButtonPanel.add(borrowingManagementButton, gbc);

        // New Button for Borrow/Return Book Form
        gbc.gridy++;
        JButton borrowReturnBookButton = createStyledButton("Borrow/Return Book");
        mainButtonPanel.add(borrowReturnBookButton, gbc);

        gbc.gridy++;
        JButton fineManagementButton = createStyledButton("Fine Management");
        mainButtonPanel.add(fineManagementButton, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setBackground(new Color(200, 80, 80));
        logoutButton.setForeground(Color.WHITE);
        mainButtonPanel.add(logoutButton, gbc);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(mainButtonPanel);
        backgroundPanel.add(centerWrapper, BorderLayout.CENTER);

        // --- Action Listeners ---
        bookManagementButton.addActionListener(e -> {
            System.out.println("LibraryDashboard: Opening Book Management...");
            new BookManagementForm(this.loggedInUser).setVisible(true);
            this.dispose();
        });

        memberManagementButton.addActionListener(e -> {
            System.out.println("LibraryDashboard: Opening Member Management...");
            new MemberManagementForm(this.loggedInUser).setVisible(true);
            this.dispose();
        });

        borrowingManagementButton.addActionListener(e -> {
            System.out.println("LibraryDashboard: Opening Borrowing Management...");
            new BorrowingManagementForm(this.loggedInUser).setVisible(true);
            this.dispose();
        });

        // Action Listener for the new Borrow/Return Book button
        borrowReturnBookButton.addActionListener(e -> {
            System.out.println("LibraryDashboard: Opening Borrow/Return Book Form...");
            new BorrowReturnBookForm().setVisible(true);
            // No dispose() here if you want the dashboard to remain open
            // If you want the dashboard to close, uncomment the line below:
            // this.dispose();
        });

        fineManagementButton.addActionListener(e -> {
            System.out.println("LibraryDashboard: Opening Fine Management...");
            new FineManagementForm(this.loggedInUser).setVisible(true);
            this.dispose();
        });

        logoutButton.addActionListener(e -> {
            System.out.println("LibraryDashboard: Logging out...");
            JOptionPane.showMessageDialog(this, "Logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
            new LibraryLoginForm().setVisible(true);
            this.dispose();
        });
        System.out.println("LibraryDashboard: UI Setup complete.");
    }

    public LibraryDashboard() {
        this(new LibraryUser("defaultGuest", "password", "Default Guest", "guest"));
        System.out.println("LibraryDashboard: Default constructor used.");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 102, 102));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 55));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Border line = BorderFactory.createLineBorder(new Color(0, 76, 76), 1);
        Border padding = BorderFactory.createEmptyBorder(10, 25, 10, 25);
        button.setBorder(BorderFactory.createCompoundBorder(line, padding));

        Color originalBg = button.getBackground();
        Color hoverBg = new Color(220, 240, 240);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus L&F not found, using default.");
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("LibraryDashboard: Main method running, creating test user dashboard.");
            LibraryUser testUser = new LibraryUser("adminUser", "adminpass", "Administrator", "admin");
            new LibraryDashboard(testUser).setVisible(true);
        });
    }
}