/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.lib.gui;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate; // For handling dates
import java.time.temporal.ChronoUnit; // For calculating days

public class BorrowReturnBookForm extends JFrame {

    private JTextField bookIdField;
    private JTextField memberIdField;
    private JTextField borrowDateField; // New field for borrow date
    private JTextField dueDateField;    // New field for due date
    private JLabel fineLabel;           // New label for fine calculation

    // You'll need a service layer for handling book borrowing/returning logic
    // For now, we'll use a placeholder
    // private BorrowingService borrowingService;

    public BorrowReturnBookForm() {
        setTitle("Library - Borrow/Return Book");
        setSize(550, 450); // Increased size for more fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 128, 128)); // Dark teal background
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Book Borrowing & Returning", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26)); // Slightly larger font
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0)); // More padding
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 15, 15)); // Adjusted grid layout
        formPanel.setBackground(new Color(30, 30, 30)); // Darker panel background
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50)); // More padding

        JLabel lblBookId = new JLabel("Book ID:");
        JLabel lblMemberId = new JLabel("Member ID:");
        JLabel lblBorrowDate = new JLabel("Borrow Date (YYYY-MM-DD):");
        JLabel lblDueDate = new JLabel("Due Date (YYYY-MM-DD):"); // New label
        JLabel lblFine = new JLabel("Overdue Fine:");

        bookIdField = new JTextField();
        memberIdField = new JTextField();
        borrowDateField = new JTextField(LocalDate.now().toString()); // Default to current date
        dueDateField = new JTextField(); // Will be calculated or set by user
        fineLabel = new JLabel("0.00"); // Display calculated fine

        // Style labels
        for (JLabel lbl : new JLabel[]{lblBookId, lblMemberId, lblBorrowDate, lblDueDate, lblFine, fineLabel}) {
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        }
        fineLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Make fine stand out

        // Style text fields
        JTextField[] fields = {bookIdField, memberIdField, borrowDateField, dueDateField};
        for (JTextField field : fields) {
            field.setBackground(new Color(60, 60, 60)); // Dark text field background
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
            field.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 150), 1)); // Slightly brighter teal border
            field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        }

        formPanel.add(lblBookId); formPanel.add(bookIdField);
        formPanel.add(lblMemberId); formPanel.add(memberIdField);
        formPanel.add(lblBorrowDate); formPanel.add(borrowDateField);
        formPanel.add(lblDueDate); formPanel.add(dueDateField);
        formPanel.add(lblFine); formPanel.add(fineLabel); // Add fine label

        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton calculateFineButton = new JButton("Calculate Fine"); // New button

        // Style buttons
        for (JButton btn : new JButton[]{borrowButton, returnButton, calculateFineButton}) {
            btn.setBackground(new Color(0, 170, 170)); // Brighter teal for buttons
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 15));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        formPanel.add(borrowButton);
        formPanel.add(returnButton);
        formPanel.add(calculateFineButton); // Add new button

        add(formPanel, BorderLayout.CENTER);

        // --- Action Listeners ---

        // Borrow Book action
        borrowButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            String memberId = memberIdField.getText().trim();
            String borrowDateStr = borrowDateField.getText().trim();
            String dueDateStr = dueDateField.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty() || borrowDateStr.isEmpty() || dueDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate borrowDate = LocalDate.parse(borrowDateStr);
                LocalDate dueDate = LocalDate.parse(dueDateStr);

                // Here you would call your service layer to handle borrowing
                // For demonstration, we'll just show a success message
                JOptionPane.showMessageDialog(this, "Book '" + bookId + "' borrowed by member '" + memberId + "' until " + dueDateStr + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error borrowing book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Return Book action
        returnButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            String memberId = memberIdField.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Book ID and Member ID for return.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Here you would call your service layer to handle returning
            // Also, you'd typically calculate the fine at the time of return based on due date
            // For demonstration, we'll just show a success message
            JOptionPane.showMessageDialog(this, "Book '" + bookId + "' returned by member '" + memberId + "'. Fine: " + fineLabel.getText(), "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        });

        // Calculate Fine action
        calculateFineButton.addActionListener(e -> {
            String borrowDateStr = borrowDateField.getText().trim();
            String dueDateStr = dueDateField.getText().trim();

            if (borrowDateStr.isEmpty() || dueDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Borrow Date and Due Date to calculate fine.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate borrowDate = LocalDate.parse(borrowDateStr);
                LocalDate dueDate = LocalDate.parse(dueDateStr);
                LocalDate today = LocalDate.now();

                double fine = 0.0;
                if (today.isAfter(dueDate)) {
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
                    // Example fine calculation: $0.50 per day overdue
                    fine = daysOverdue * 0.50;
                }
                fineLabel.setText(String.format("%.2f", fine));

            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                fineLabel.setText("0.00"); // Reset fine on error
            }
        });
    }

    private void clearForm() {
        bookIdField.setText("");
        memberIdField.setText("");
        borrowDateField.setText(LocalDate.now().toString());
        dueDateField.setText("");
        fineLabel.setText("0.00");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BorrowReturnBookForm().setVisible(true));
    }
}