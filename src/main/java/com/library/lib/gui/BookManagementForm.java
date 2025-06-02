package com.library.lib.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import com.library.lib.model.Book;
import com.library.lib.model.LibraryUser;
import com.library.lib.service.BookService;
import com.library.lib.dao.InMemoryBookDaoImpl; // Corrected import
import java.util.ArrayList;

public class BookManagementForm extends JFrame {

    private JTextField bookIdField, titleField, authorField, isbnField, categoryField, totalCopiesField, availableCopiesField;
    private JButton addButton, viewAllButton, updateButton, deleteButton, searchButton, backButton;
    private JTextArea displayArea;
    private BookService bookService;
    private LibraryUser loggedInUser;

    public BookManagementForm(LibraryUser user) {
        this.loggedInUser = user;
        // Instantiate BookService with your in-memory DAO
        this.bookService = new BookService(new InMemoryBookDaoImpl() {
            @Override
            public List<Book> searchBooks(String query) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void decreaseBookQuantity(String bookId, int quantity) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void increaseBookQuantity(String bookId, int quantity) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        }); // Use the full implementation now

        setTitle("Book Management - Library System");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only dispose this window

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(0, 102, 102, 180); // Darker teal
                Color color2 = new Color(0, 153, 153, 180); // Lighter teal
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(0, 20));
        setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel("Book Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Book ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("Book ID:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        bookIdField = new JTextField(25); styleTextField(bookIdField);
        formPanel.add(bookIdField, gbc);

        // Title
        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        titleField = new JTextField(25); styleTextField(titleField);
        formPanel.add(titleField, gbc);

        // Author
        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        authorField = new JTextField(25); styleTextField(authorField);
        formPanel.add(authorField, gbc);

        // ISBN
        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        isbnField = new JTextField(25); styleTextField(isbnField);
        formPanel.add(isbnField, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        categoryField = new JTextField(25); styleTextField(categoryField);
        formPanel.add(categoryField, gbc);

        // Total Copies
        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("Total Copies:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        totalCopiesField = new JTextField(25); styleTextField(totalCopiesField);
        formPanel.add(totalCopiesField, gbc);

        // Available Copies
        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(createStyledLabel("Available Copies:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        availableCopiesField = new JTextField(25); styleTextField(availableCopiesField);
        availableCopiesField.setEditable(false); // Available copies should be calculated, not directly set
        formPanel.add(availableCopiesField, gbc);

        backgroundPanel.add(formPanel, BorderLayout.CENTER);

        JPanel crudButtonsPanel = new JPanel();
        crudButtonsPanel.setOpaque(false);
        crudButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        addButton = createStyledButton("Add Book");
        viewAllButton = createStyledButton("View All Books");
        updateButton = createStyledButton("Update Book");
        deleteButton = createStyledButton("Delete Book");
        searchButton = createStyledButton("Search by ID/ISBN");

        crudButtonsPanel.add(addButton);
        crudButtonsPanel.add(viewAllButton);
        crudButtonsPanel.add(updateButton);
        crudButtonsPanel.add(deleteButton);
        crudButtonsPanel.add(searchButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        backButton = createStyledButton("Back to Dashboard");
        JPanel backButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonWrapper.setOpaque(false);
        backButtonWrapper.add(backButton);

        displayArea = new JTextArea(10, 60);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayArea.setBackground(new Color(240, 255, 255)); // Light cyan background for text area
        displayArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Book List",
                0, 0, new Font("Times New Roman", Font.BOLD, 14), Color.WHITE));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Make viewport transparent

        bottomPanel.add(crudButtonsPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(backButtonWrapper, BorderLayout.SOUTH);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addBook());
        viewAllButton.addActionListener(e -> viewAllBooks());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        searchButton.addActionListener(e -> searchBook());
        backButton.addActionListener(e -> {
            new LibraryDashboard(this.loggedInUser).setVisible(true);
            this.dispose();
        });

        // Initialize with all books displayed
        viewAllBooks();
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
        // Add hover effect
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

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 170, 170)));
        field.setBackground(new Color(60, 60, 60)); // Dark background for input
        field.setForeground(Color.WHITE); // White text
        field.setCaretColor(Color.WHITE); // White caret
    }

    private void addBook() {
        try {
            String bookId = bookIdField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            String category = categoryField.getText().trim();
            int totalCopies = Integer.parseInt(totalCopiesField.getText().trim());

            if (bookId.isEmpty() || title.isEmpty() || author.isEmpty() || isbn.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all text fields (except Available Copies) to add a book.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book newBook = new Book(bookId, title, author, isbn, category, totalCopies, totalCopies); // Initially available copies = total copies
            boolean success = bookService.addBook(newBook);

            if (success) {
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                clearFields();
                viewAllBooks(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book. Book ID or ISBN might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Total Copies.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void viewAllBooks() {
        displayArea.setText("");
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            displayArea.setText("No books registered yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-25s %-20s %-15s %-15s %-10s %-10s%n",
                "BOOK ID", "TITLE", "AUTHOR", "ISBN", "CATEGORY", "TOTAL", "AVAIL"));
        sb.append("------------------------------------------------------------------------------------------------------------------\n");
        for (Book book : books) {
            sb.append(String.format("%-10s %-25s %-20s %-15s %-15s %-10d %-10d%n",
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                    book.getCategory(), book.getTotalCopies(), book.getAvailableCopies()));
        }
        displayArea.setText(sb.toString());
    }

    private void updateBook() {
        try {
            String bookId = bookIdField.getText().trim();
            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Book ID of the book to update.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Book existingBook = bookService.getBookById(bookId); // Use getBookById
            if (existingBook == null) {
                // If not found by ID, try ISBN if provided
                String isbnToSearch = isbnField.getText().trim();
                if (!isbnToSearch.isEmpty()) {
                    existingBook = bookService.getBookByIsbn(isbnToSearch); // Use getBookByIsbn
                    if (existingBook != null) {
                        bookId = existingBook.getBookId(); // Update bookId to match found book
                    }
                }
            }

            if (existingBook == null) {
                JOptionPane.showMessageDialog(this, "Book with ID/ISBN not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Update fields only if they are not empty in the form
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            String category = categoryField.getText().trim();
            String totalCopiesStr = totalCopiesField.getText().trim();

            if (!title.isEmpty()) existingBook.setTitle(title);
            if (!author.isEmpty()) existingBook.setAuthor(author);
            if (!isbn.isEmpty()) existingBook.setIsbn(isbn);
            if (!category.isEmpty()) existingBook.setCategory(category);
            if (!totalCopiesStr.isEmpty()) {
                try {
                    int newTotalCopies = Integer.parseInt(totalCopiesStr);
                    // Adjust available copies based on change in total copies, maintaining borrowed count
                    int borrowedCopies = existingBook.getTotalCopies() - existingBook.getAvailableCopies();
                    existingBook.setTotalCopies(newTotalCopies);
                    existingBook.setAvailableCopies(Math.max(0, newTotalCopies - borrowedCopies));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid number for Total Copies.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            boolean success = bookService.updateBook(existingBook);

            if (success) {
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
                clearFields();
                viewAllBooks(); // Refresh list
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteBook() {
        String bookId = bookIdField.getText().trim();
        String isbn = isbnField.getText().trim(); // Allow deletion by ISBN too

        if (bookId.isEmpty() && isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Book ID or ISBN of the book to delete.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book bookToDelete = null;
        if (!bookId.isEmpty()) {
            bookToDelete = bookService.getBookById(bookId);
        }
        if (bookToDelete == null && !isbn.isEmpty()) {
            bookToDelete = bookService.getBookByIsbn(isbn);
        }

        if (bookToDelete == null) {
            JOptionPane.showMessageDialog(this, "Book with provided ID or ISBN not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete book: \"" + bookToDelete.getTitle() + "\" (ID: " + bookToDelete.getBookId() + ")?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = bookService.deleteBook(bookToDelete.getBookId());
                if (success) {
                    JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                    clearFields();
                    viewAllBooks(); // Refresh list
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete book.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void searchBook() {
        String searchId = bookIdField.getText().trim();
        String searchIsbn = isbnField.getText().trim();
        String searchText = titleField.getText().trim(); // Also allow searching by title/author/category
        String searchAuthor = authorField.getText().trim();
        String searchCategory = categoryField.getText().trim();

        if (searchId.isEmpty() && searchIsbn.isEmpty() && searchText.isEmpty() && searchAuthor.isEmpty() && searchCategory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Book ID, ISBN, Title, Author, or Category to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Book> foundBooks = new ArrayList<>();

        // Prioritize specific searches (ID, ISBN) then broader searches
        if (!searchId.isEmpty()) {
            Book book = bookService.getBookById(searchId);
            if (book != null) {
                foundBooks.add(book);
            }
        } else if (!searchIsbn.isEmpty()) {
            Book book = bookService.getBookByIsbn(searchIsbn);
            if (book != null) {
                foundBooks.add(book);
            }
        } else if (!searchText.isEmpty()) {
            // General search across multiple fields
            foundBooks.addAll(bookService.searchBooks(searchText));
        } else if (!searchAuthor.isEmpty()) {
            foundBooks.addAll(bookService.searchBooks(searchAuthor));
        } else if (!searchCategory.isEmpty()) {
            foundBooks.addAll(bookService.searchBooks(searchCategory));
        }

        displayArea.setText("");
        if (foundBooks.isEmpty()) {
            displayArea.setText("No books found matching your search criteria.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-25s %-20s %-15s %-15s %-10s %-10s%n",
                "BOOK ID", "TITLE", "AUTHOR", "ISBN", "CATEGORY", "TOTAL", "AVAIL"));
        sb.append("------------------------------------------------------------------------------------------------------------------\n");
        for (Book book : foundBooks) {
            sb.append(String.format("%-10s %-25s %-20s %-15s %-15s %-10d %-10d%n",
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                    book.getCategory(), book.getTotalCopies(), book.getAvailableCopies()));
        }
        displayArea.setText(sb.toString());
    }

    private void clearFields() {
        bookIdField.setText("");
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        categoryField.setText("");
        totalCopiesField.setText("");
        availableCopiesField.setText("");
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
            // For testing BookManagementForm directly
            LibraryUser testUser = new LibraryUser("testAdmin", "password", "Test Admin", "admin");
            new BookManagementForm(testUser).setVisible(true);
        });
    }
}