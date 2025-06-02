// File: com/library/lib/dao/BookDaoImpl.java
package com.library.lib.dao;

import com.library.lib.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This example still uses HashMap for demonstration; replace with actual DB logic if applicable
public abstract class BookDaoImpl implements BookDao {

    private Map<String, Book> books = new HashMap<>(); // Placeholder for DB interaction

    public BookDaoImpl() {
        // This constructor would typically initialize DB connection or similar
        // For testing, keeping some dummy data
        books.put("B001", new Book("B001", "Database Book 1", "DB Author 1", "978-0000000001", "DB Category", 10, 10));
    }

    @Override
    public boolean addBook(Book book) {
        System.out.println("DB DAO: Adding book: " + book.getTitle());
        return books.putIfAbsent(book.getBookId(), book) == null;
    }

    @Override
    public Book getBookById(String bookId) {
        return books.get(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public boolean updateBook(Book book) {
        if (books.containsKey(book.getBookId())) {
            books.put(book.getBookId(), book);
            System.out.println("DB DAO: Updated book: " + book.getTitle());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBook(String bookId) {
        return books.remove(bookId) != null;
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        for (Book book : books.values()) {
            if (book.getBookId().toLowerCase().contains(lowerCaseQuery) ||
                book.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                book.getAuthor().toLowerCase().contains(lowerCaseQuery) ||
                book.getIsbn().toLowerCase().contains(lowerCaseQuery) ||
                book.getCategory().toLowerCase().contains(lowerCaseQuery)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override // Corrected: Must have two parameters
    public void decreaseBookQuantity(String bookId, int quantity) {
        Book book = books.get(bookId);
        if (book != null) {
            if (book.getAvailableCopies() >= quantity) {
                book.setAvailableCopies(book.getAvailableCopies() - quantity);
                System.out.println("DB DAO: Decreased quantity for book: " + book.getTitle());
            }
        }
    }

    @Override // Corrected: Must have two parameters
    public void increaseBookQuantity(String bookId, int quantity) {
        Book book = books.get(bookId);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + quantity);
            System.out.println("DB DAO: Increased quantity for book: " + book.getTitle());
            // Also update in DB here
        }
    }
}