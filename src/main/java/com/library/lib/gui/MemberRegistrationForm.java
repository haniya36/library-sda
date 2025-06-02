package com.library.lib.gui;

import com.library.lib.model.LibraryUser;
import com.library.lib.service.AuthService; // Using AuthService for registration

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberRegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField; // Assuming you collect full name
    private JButton registerButton;
    private JButton backToLoginButton;

    private AuthService authService;

    public MemberRegistrationForm() {
        authService = new AuthService(); // Initialize AuthService

        setTitle("New Member Registration");
        setSize(400, 450); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close just this window
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 128, 128)); // Teal color
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Register New Member", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Username
        JLabel usernameLabel = createStyledLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; mainPanel.add(usernameLabel, gbc);
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = createStyledLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(passwordField, gbc);

        // Full Name (assuming LibraryUser has this)
        JLabel fullNameLabel = createStyledLabel("Full Name:");
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(fullNameLabel, gbc);
        fullNameField = new JTextField(20);
        fullNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(fullNameField, gbc);

        // Register Button
        registerButton = new JButton("Register Account");
        styleButton(registerButton);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(registerButton, gbc);

        // Back to Login Button
        backToLoginButton = new JButton("Back to Login");
        styleLinkButton(backToLoginButton); // Reusing link button style
        gbc.gridy = 5;
        mainPanel.add(backToLoginButton, gbc);


        // --- Action Listeners ---
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerNewMember();
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close this registration form and open the login form
                new LibraryLoginForm().setVisible(true);
                dispose();
            }
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        return label;
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 128, 128));
        button.setFont(new Font("Times New Roman", Font.BOLD, 14));
        button.setFocusPainted(false);
    }
    
    private void styleLinkButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(new Color(224, 247, 250)); // Lighter teal text
        btn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }


    private void registerNewMember() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Basic password strength check (optional)
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new LibraryUser object with default role "member"
        LibraryUser newUser = new LibraryUser(username, password, fullName, "member");

        // Use AuthService to register the user
        boolean success = authService.registerUser(newUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // After successful registration, navigate back to the login form
            new LibraryLoginForm().setVisible(true);
            this.dispose(); // Close the registration form
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Username might already be taken.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Main method for testing this form individually (optional)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MemberRegistrationForm().setVisible(true);
        });
    }
}