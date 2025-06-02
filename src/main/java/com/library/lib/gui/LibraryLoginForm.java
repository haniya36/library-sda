package com.library.lib.gui;

import com.library.lib.model.AuthService;
import com.library.lib.model.LibraryUser; // Ensure this path is correct

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Needed for older action listener syntax if you revert lambda

public class LibraryLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerLink;
    private JButton homeLink;
    private AuthService authService;

    private static final String IMAGE_PATH = "/resources/library_bg.jpeg"; // Example path, adjust as needed

    public LibraryLoginForm() {
        authService = new AuthService(); // Initialize AuthService

        setTitle("Library Management System - Login");
        setSize(420, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon backgroundImage = null;
        try {
            java.net.URL imgURL = getClass().getResource(IMAGE_PATH);
            if (imgURL != null) {
                backgroundImage = new ImageIcon(imgURL);
            } else {
                System.out.println("Warning: Background image not found at " + IMAGE_PATH + ". Using fallback color.");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            System.out.println("Using fallback color.");
        }

        if (backgroundImage != null) {
            final ImageIcon finalBackgroundImage = backgroundImage;
            setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(finalBackgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            });
        } else {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(0, 100, 100));
            setContentPane(panel);
        }
        setLayout(new GridBagLayout());

        JPanel overlay = new JPanel();
        overlay.setBackground(new Color(0, 128, 128, 180));
        overlay.setPreferredSize(new Dimension(360, 460));
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Library System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlay.add(titleLabel);

        JLabel tagline = new JLabel("Access Your Library Account", SwingConstants.CENTER);
        tagline.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tagline.setForeground(new Color(224, 247, 250));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        overlay.add(Box.createVerticalStrut(15));
        overlay.add(tagline);
        overlay.add(Box.createVerticalStrut(30));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new GridLayout(0, 1, 15, 15));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        form.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0, 170, 170)));
        usernameField.setBackground(new Color(60, 60, 60));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        form.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        form.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 170, 170)));
        passwordField.setBackground(new Color(60, 60, 60));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        form.add(passwordField);

        overlay.add(form);
        overlay.add(Box.createVerticalStrut(30));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(150, 45));
        loginButton.setMaximumSize(new Dimension(250, 60));
        loginButton.setBackground(new Color(0, 190, 190));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> loginUser());
        overlay.add(loginButton);

        overlay.add(Box.createVerticalStrut(25));

        registerLink = new JButton("New to the Library? Register Here");
        styleLinkButton(registerLink);
        registerLink.addActionListener(e -> {
            new MemberRegistrationForm().setVisible(true);
            this.dispose();
        });
        overlay.add(registerLink);

        homeLink = new JButton("Back to Main Screen");
        styleLinkButton(homeLink);
        homeLink.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Functionality to go to a general main screen is not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        overlay.add(homeLink);

        add(overlay);
    }

    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryUser user = authService.authenticateUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getFullName() + ".", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new LibraryDashboard(user).setVisible(true); // Opens the dashboard here
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleLinkButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(new Color(224, 247, 250));
        btn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryLoginForm().setVisible(true);
        });
    }
}