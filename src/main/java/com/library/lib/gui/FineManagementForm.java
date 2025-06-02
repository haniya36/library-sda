package com.library.lib.gui;

import com.library.lib.dao.InMemoryFineDaoImpl;
import com.library.lib.model.Fine;
import com.library.lib.service.FineService;
import com.library.lib.model.LibraryUser; // Import LibraryUser
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

public class FineManagementForm extends JFrame {
    private JTextField fineIdField, memberIdField, borrowingRecordIdField, amountField;
    private JCheckBox paidCheckBox;
    private JButton assessFineButton, payFineButton, viewAllButton, viewUnpaidButton, backButton;
    private JTextArea displayArea;
    private FineService fineService;
    private LibraryUser loggedInUser; // Added field to store the logged-in user

    // New constructor to accept LibraryUser
    public FineManagementForm(LibraryUser loggedInUser) {
        this(); // Call the no-arg constructor to set up the UI
        this.loggedInUser = loggedInUser;
        System.out.println("FineManagementForm: User received: " + loggedInUser.getUsername());
        // You can use loggedInUser here if you need to restrict features based on roles
    }

    public FineManagementForm() {
        this.fineService = new FineService(new InMemoryFineDaoImpl());

        setTitle("Fine Management - Library System");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 128, 128, 180);
                Color color2 = new Color(0, 128, 128, 180);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(0, 20));
        setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel("Fine Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel fineIdLabel = createStyledLabel("Fine ID:");
        fineIdField = new JTextField(20);
        fineIdField.setEditable(false);
        fineIdField.setText(UUID.randomUUID().toString().substring(0, 8));

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        memberIdField = new JTextField(20);
        JLabel borrowingRecordIdLabel = createStyledLabel("Borrowing Record ID:");
        borrowingRecordIdField = new JTextField(20);
        JLabel amountLabel = createStyledLabel("Amount:");
        amountField = new JTextField(20);
        JLabel paidLabel = createStyledLabel("Paid:");
        paidCheckBox = new JCheckBox();
        paidCheckBox.setOpaque(false);
        paidCheckBox.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(fineIdLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(fineIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(memberIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(borrowingRecordIdLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(borrowingRecordIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(paidLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(paidCheckBox, gbc);

        backgroundPanel.add(formPanel, BorderLayout.CENTER);

        JPanel crudButtonsPanel = new JPanel();
        crudButtonsPanel.setOpaque(false);
        crudButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        assessFineButton = createStyledButton("Assess New Fine");
        payFineButton = createStyledButton("Mark Fine as Paid");
        viewAllButton = createStyledButton("View All Fines");
        viewUnpaidButton = createStyledButton("View Unpaid Fines");

        crudButtonsPanel.add(assessFineButton);
        crudButtonsPanel.add(payFineButton);
        crudButtonsPanel.add(viewAllButton);
        crudButtonsPanel.add(viewUnpaidButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        backButton = createStyledButton("Back to Dashboard");
        JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonWrapper.setOpaque(false);
        backButtonWrapper.add(backButton);

        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Fine Records",
                0, 0, new Font("Times New Roman", Font.BOLD, 14), Color.WHITE));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        bottomPanel.add(crudButtonsPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(backButtonWrapper, BorderLayout.SOUTH);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        assessFineButton.addActionListener(e -> assessFine());
        payFineButton.addActionListener(e -> payFine());
        viewAllButton.addActionListener(e -> viewAllFines());
        viewUnpaidButton.addActionListener(e -> viewUnpaidFines());
        backButton.addActionListener(e -> {
            // Pass the loggedInUser back to the dashboard
            new LibraryDashboard(this.loggedInUser).setVisible(true);
            this.dispose();
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Times New Roman", Font.BOLD, 16));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 128, 128));
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setFocusPainted(false);
        return button;
    }

    private void assessFine() {
        try {
            String fineId = fineIdField.getText().trim();
            String memberId = memberIdField.getText().trim();
            String borrowingRecordId = borrowingRecordIdField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());

            if (fineId.isEmpty() || memberId.isEmpty() || borrowingRecordId.isEmpty() || amount <= 0) {
                JOptionPane.showMessageDialog(this, "All fields (except Paid Status) are required and amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Fine newFine = new Fine(fineId, memberId, borrowingRecordId, amount, paidCheckBox.isSelected(), LocalDate.now());

            boolean success = fineService.assessFine(newFine);

            if (success) {
                JOptionPane.showMessageDialog(this, "Fine assessed successfully!");
                clearFields();
                fineIdField.setText(UUID.randomUUID().toString().substring(0, 8)); // Generate new ID for next entry
                viewAllFines(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to assess fine.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void payFine() {
        String fineId = JOptionPane.showInputDialog(this, "Enter Fine ID to mark as paid:");
        if (fineId == null || fineId.trim().isEmpty()) {
            return;
        }
        try {
            boolean success = fineService.payFine(fineId.trim());
            if (success) {
                JOptionPane.showMessageDialog(this, "Fine with ID " + fineId.trim() + " marked as paid successfully!");
                viewUnpaidFines(); // Refresh list to show paid fines are gone
            } else {
                JOptionPane.showMessageDialog(this, "Failed to mark fine as paid. Check Fine ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void viewAllFines() {
        displayArea.setText("");
        java.util.List<Fine> fines = fineService.getAllFines();
        if (fines.isEmpty()) {
            displayArea.setText("No fine records found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-22s %-10s %-12s %-8s%n",
                "FINE ID", "MEMBER ID", "BORROWING RECORD ID", "AMOUNT", "DATE", "PAID"));
        sb.append("-----------------------------------------------------------------------------------------------%n");
        for (Fine fine : fines) {
            sb.append(String.format("%-10s %-15s %-22s %-10.2f %-12s %-8s%n",
                    fine.getFineId().substring(0, Math.min(fine.getFineId().length(), 8)),
                    fine.getMemberId(),
                    fine.getBorrowingRecordId().substring(0, Math.min(fine.getBorrowingRecordId().length(), 20)),
                    fine.getAmount(),
                    fine.getFineDate(),
                    fine.isPaid() ? "Yes" : "No"));
        }
        displayArea.setText(sb.toString());
    }

    private void viewUnpaidFines() {
        displayArea.setText("");
        java.util.List<Fine> unpaidFines = fineService.getUnpaidFines();
        if (unpaidFines.isEmpty()) {
            displayArea.setText("No unpaid fines found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-22s %-10s %-12s%n",
                "FINE ID", "MEMBER ID", "BORROWING RECORD ID", "AMOUNT", "DATE"));
        sb.append("-------------------------------------------------------------------------------%n");
        for (Fine fine : unpaidFines) {
            sb.append(String.format("%-10s %-15s %-22s %-10.2f %-12s%n",
                    fine.getFineId().substring(0, Math.min(fine.getFineId().length(), 8)),
                    fine.getMemberId(),
                    fine.getBorrowingRecordId().substring(0, Math.min(fine.getBorrowingRecordId().length(), 20)),
                    fine.getAmount(),
                    fine.getFineDate()));
        }
        displayArea.setText(sb.toString());
    }

    private void clearFields() {
        memberIdField.setText("");
        borrowingRecordIdField.setText("");
        amountField.setText("");
        paidCheckBox.setSelected(false);
    }

    // Main method for testing this form independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a dummy user for independent testing
            LibraryUser testUser = new LibraryUser("testFineUser", "pass", "Test Finer", "admin");
            new FineManagementForm(testUser).setVisible(true);
        });
    }
}