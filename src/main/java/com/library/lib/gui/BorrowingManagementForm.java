package com.library.lib.gui;

import com.library.lib.model.BorrowingRecord;
import com.library.lib.service.BorrowingService;
import com.library.lib.model.Book;
import com.library.lib.model.LibraryUser; // Keep this import
import com.library.lib.dao.InMemoryBookDaoImpl;
import com.library.lib.dao.InMemoryMemberDaoImpl;
import com.library.lib.dao.InMemoryBorrowingRecordDaoImpl;
import com.library.lib.dao.InMemoryFineDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BorrowingManagementForm extends JFrame {
    private JTextField memberIdField, bookIsbnField, recordIdField;
    private JButton issueButton, returnButton, viewAllButton, backButton;
    private JTextArea displayArea;
    private BorrowingService borrowingService;
    private LibraryUser loggedInUser; // Added field to store the logged-in user

    // New constructor to accept LibraryUser
    public BorrowingManagementForm(LibraryUser loggedInUser) {
        this(); // Call the no-arg constructor to set up the UI
        this.loggedInUser = loggedInUser;
        System.out.println("BorrowingManagementForm: User received: " + loggedInUser.getUsername());
        // You can use loggedInUser here if you need to restrict features based on roles
    }

    public BorrowingManagementForm() {
        // Initialize BorrowingService with necessary concrete DAOs
        this.borrowingService = new BorrowingService(
                new InMemoryBookDaoImpl() {
                    // It's highly recommended to implement these methods properly
                    // rather than throwing UnsupportedOperationException
                    @Override
                    public Book getBookByIsbn(String bn) {
                        // Implement actual logic to retrieve a book by ISBN
                        System.out.println("InMemoryBookDaoImpl: getBookByIsbn called for " + bn);
                        // For testing, return a dummy book or null
                        return null;
                    }

                    @Override
                    public void increaseBookQuantity(String bookIsbn) {
                        System.out.println("InMemoryBookDaoImpl: increaseBookQuantity called for " + bookIsbn);
                        // Implement logic
                    }

                    @Override
                    public void decreaseBookQuantity(String bookIsbn) {
                        System.out.println("InMemoryBookDaoImpl: decreaseBookQuantity called for " + bookIsbn);
                        // Implement logic
                    }

                    @Override
                    public List<Book> searchBooks(String query) {
                        System.out.println("InMemoryBookDaoImpl: searchBooks called for " + query);
                        return new java.util.ArrayList<>();
                    }

                    @Override
                    public void decreaseBookQuantity(String bookId, int quantity) {
                        System.out.println("InMemoryBookDaoImpl: decreaseBookQuantity called for " + bookId + ", qty: " + quantity);
                    }

                    @Override
                    public void increaseBookQuantity(String bookId, int quantity) {
                        System.out.println("InMemoryBookDaoImpl: increaseBookQuantity called for " + bookId + ", qty: " + quantity);
                    }
                },
                new InMemoryMemberDaoImpl(),
                new InMemoryBorrowingRecordDaoImpl(),
                new InMemoryFineDaoImpl()
        );

        setTitle("Borrowing & Return Management - Library System");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only dispose this window
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 20));

        // Background panel with teal gradient
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

        // Title label
        JLabel titleLabel = new JLabel("Borrowing & Return Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel for Issue/Return
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel memberIdLabel = createStyledLabel("Member ID:");
        memberIdField = new JTextField(20);
        JLabel bookIsbnLabel = createStyledLabel("Book ISBN:");
        bookIsbnField = new JTextField(20);
        JLabel recordIdLabel = createStyledLabel("Record ID (for return):");
        recordIdField = new JTextField(20);
        recordIdField.setToolTipText("Enter Record ID to return a book");

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(memberIdLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(memberIdField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(bookIsbnLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(bookIsbnField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(recordIdLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(recordIdField, gbc);

        backgroundPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel for Issue/Return
        JPanel crudButtonsPanel = new JPanel();
        crudButtonsPanel.setOpaque(false);
        crudButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        issueButton = createStyledButton("Issue Book");
        returnButton = createStyledButton("Return Book");
        viewAllButton = createStyledButton("View All Borrowings");

        crudButtonsPanel.add(issueButton);
        crudButtonsPanel.add(returnButton);
        crudButtonsPanel.add(viewAllButton);

        // Back button panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        backButton = createStyledButton("Back to Dashboard");
        JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonWrapper.setOpaque(false);
        backButtonWrapper.add(backButton);

        // Display area for results
        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Borrowing Records",
                0, 0, new Font("Times New Roman", Font.BOLD, 14), Color.WHITE));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        bottomPanel.add(crudButtonsPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(backButtonWrapper, BorderLayout.SOUTH);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        issueButton.addActionListener(e -> issueBook());
        returnButton.addActionListener(e -> returnBook());
        viewAllButton.addActionListener(e -> viewAllBorrowingRecords());
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

    private void issueBook() {
        try {
            String memberId = memberIdField.getText().trim();
            String bookIsbn = bookIsbnField.getText().trim();

            if (memberId.isEmpty() || bookIsbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Member ID and Book ISBN cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = borrowingService.issueBook(memberId, bookIsbn);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book issued successfully!");
                clearFields();
                viewAllBorrowingRecords(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to issue book.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void returnBook() {
        try {
            String recordId = recordIdField.getText().trim();

            if (recordId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Record ID to return the book.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = borrowingService.returnBook(recordId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
                clearFields();
                viewAllBorrowingRecords(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to return book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void viewAllBorrowingRecords() {
        displayArea.setText("");
        List<BorrowingRecord> records = borrowingService.getAllBorrowingRecords();
        if (records.isEmpty()) {
            displayArea.setText("No borrowing records found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-15s %-12s %-12s %-12s %-10s%n",
                "REC_ID", "MEMBER_ID", "BOOK_ISBN", "BORROW_DATE", "DUE_DATE", "RETURN_DATE", "FINE_AMT"));
        sb.append("-----------------------------------------------------------------------------------------------%n");
        for (BorrowingRecord record : records) {
            sb.append(String.format("%-10s %-15s %-15s %-12s %-12s %-12s %-10.2f%n",
                    record.getRecordId().substring(0, Math.min(record.getRecordId().length(), 8)), // Shorten ID for display
                    record.getMemberId(),
                    record.getBookIsbn(),
                    record.getBorrowDate(),
                    record.getDueDate(),
                    record.getReturnDate() != null ? record.getReturnDate().toString() : "N/A",
                    record.getFineAmount()));
        }
        displayArea.setText(sb.toString());
    }

    private void clearFields() {
        memberIdField.setText("");
        bookIsbnField.setText("");
        recordIdField.setText("");
    }

    // Main method for testing this form independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a dummy user for independent testing
            LibraryUser testUser = new LibraryUser("testBorrowUser", "pass", "Test Borrower", "member");
            new BorrowingManagementForm(testUser).setVisible(true);
        });
    }
}