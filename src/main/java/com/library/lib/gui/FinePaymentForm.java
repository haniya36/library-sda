package com.library.lib.gui;

import com.library.lib.model.FineTransaction;
import com.library.lib.model.LibraryUser; // Import LibraryUser from the model package
import com.library.lib.service.FineService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FinePaymentForm extends JFrame {
    private JTextField memberIdField;
    private JTextField bookIdField;
    private JTextField amountField;
    private JLabel successMessage;
    private FineService fineService;
    private LibraryUser currentUser; // Store the current user

    // Constructor now accepts a LibraryUser
    public FinePaymentForm(LibraryUser user) {
        this.currentUser = user; // Store the passed-in user
        this.fineService = new FineService();

        setTitle("Library - Record Fine Payment" + (user != null && user.getUsername() != null ? " (User: " + user.getUsername() + ")" : ""));
        setSize(450, 500); // Adjusted size for better layout with back button
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close, don't exit app
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        JLabel headerLabel = new JLabel("Record Fine Payment");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout for more flexibility
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setBackground(new Color(240, 248, 255)); // AliceBlue, a lighter background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Member ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel memberIdLabel = new JLabel("Member ID:");
        styleLabel(memberIdLabel, false); // false for not white text
        formPanel.add(memberIdLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        memberIdField = new JTextField(15);
        styleTextField(memberIdField, false); // false for not dark theme
        formPanel.add(memberIdField, gbc);
        gbc.weightx = 0; // Reset weightx

        // Book ID
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel bookIdLabel = new JLabel("Book ID (Optional):");
        styleLabel(bookIdLabel, false);
        formPanel.add(bookIdLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        bookIdField = new JTextField(15);
        styleTextField(bookIdField, false);
        formPanel.add(bookIdField, gbc);
        gbc.weightx = 0;

        // Amount
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel amountLabel = new JLabel("Amount to Pay:");
        styleLabel(amountLabel, false);
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        amountField = new JTextField(15);
        styleTextField(amountField, false);
        formPanel.add(amountField, gbc);
        gbc.weightx = 0;
        
        // Pay Fine button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton payFineButton = new JButton("Record Payment");
        styleButton(payFineButton);
        formPanel.add(payFineButton, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.anchor = GridBagConstraints.WEST; // Reset anchor

        // Success message label (will be added to south panel)
        successMessage = new JLabel("", SwingConstants.CENTER); // Initially empty
        successMessage.setForeground(new Color(0, 128, 0)); // Dark Green for success
        successMessage.setFont(new Font("Times New Roman", Font.BOLD, 16));
        successMessage.setVisible(false);

        add(formPanel, BorderLayout.CENTER);

        // South panel for success message and back button
        JPanel southPanelContainer = new JPanel(new BorderLayout());
        southPanelContainer.setBackground(new Color(240, 248, 255)); // Match form panel background
        southPanelContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        southPanelContainer.add(successMessage, BorderLayout.NORTH);

        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanelSouth.setOpaque(false); // Make transparent
        JButton backButton = new JButton("Back to Dashboard");
        styleButton(backButton);
        buttonPanelSouth.add(backButton);
        southPanelContainer.add(buttonPanelSouth, BorderLayout.SOUTH);

        add(southPanelContainer, BorderLayout.SOUTH);

        // Button actions
        payFineButton.addActionListener(this::handleFinePayment);

        backButton.addActionListener(e -> {
            dispose(); // Close this FinePaymentForm
            // Create new LibraryDashboard, passing the stored currentUser
            // This assumes LibraryDashboard constructor takes LibraryUser (from model package)
            new LibraryDashboard(this.currentUser).setVisible(true); // Make the dashboard visible
        });
    }

    // Overloaded constructor for testing or if no user context is available (less ideal)
    public FinePaymentForm() {
        this(null); // Calls the main constructor with a null user
    }

    private void styleLabel(JLabel label, boolean isDarkTheme) {
        label.setForeground(isDarkTheme ? Color.WHITE : new Color(0, 102, 102)); // Teal for light theme
        label.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void styleTextField(JTextField field, boolean isDarkTheme) {
        if (isDarkTheme) {
            field.setBackground(new Color(60, 60, 60));
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
        } else {
            field.setBackground(Color.WHITE);
            field.setForeground(Color.BLACK);
            field.setCaretColor(Color.BLACK);
        }
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 128, 128)), // Teal border
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Inner padding
        ));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 128, 128)); // Teal
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0,102,102)), // Darker teal border
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // Basic hover effect
        Color originalBg = button.getBackground();
        Color hoverBg = new Color(0, 153, 153); // Lighter teal

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverBg);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
            }
        });
    }

    private void handleFinePayment(ActionEvent e) {
        String memberId = memberIdField.getText().trim();
        String bookId = bookIdField.getText().trim();
        String amountText = amountField.getText().trim();

        if (memberId.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID and Amount are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be a positive value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FineTransaction transaction = new FineTransaction(memberId, bookId.isEmpty() ? null : bookId, amount);
        // Assuming fineService.recordPayment takes this transaction and returns boolean
        boolean success = fineService.recordPayment(transaction); 

        if (success) {
            successMessage.setText("Payment of " + String.format("%.2f", amount) + " for member " + memberId + " recorded.");
            successMessage.setVisible(true);
            memberIdField.setText("");
            bookIdField.setText("");
            amountField.setText("");

            Timer timer = new Timer(4000, ev -> successMessage.setVisible(false));
            timer.setRepeats(false);
            timer.start();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to record payment. Please check details or system logs.", "Payment Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // For testing FinePaymentForm directly
        // Create a dummy LibraryUser for testing context
        LibraryUser testUser = new LibraryUser("testFineUser", "pass", "Test FinePayer", "member");
        SwingUtilities.invokeLater(() -> new FinePaymentForm(testUser).setVisible(true));
    }
}
