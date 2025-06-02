package com.library.lib.dao;

import com.library.lib.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InMemoryBookDaoImpl implements BookDao {

    // Using ConcurrentHashMap for thread-safety, though for simple UI it might not be strictly necessary
    // Maps ISBN to Book object for quick lookup.
    private final Map<String, Book> booksByIsbn = new ConcurrentHashMap<>();
    // You might also want a map for bookId if it's different from ISBN and used for unique identification
    private final Map<String, Book> booksById = new ConcurrentHashMap<>();

    public InMemoryBookDaoImpl() {
        // Optional: Add some dummy data for testing
        addBook(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", "Classic", 5, 5));
        addBook(new Book("B002", "1984", "George Orwell", "978-0451524935", "Dystopian", 3, 3));
        addBook(new Book("B003", "To Kill a Mockingbird", "Harper Lee", "978-0061120084", "Classic", 4, 4));
    }

    @Override
    public boolean addBook(Book book) {
        if (booksById.containsKey(book.getBookId()) || booksByIsbn.containsKey(book.getIsbn())) {
            return false; // Book with same ID or ISBN already exists
        }
        booksById.put(book.getBookId(), book);
        booksByIsbn.put(book.getIsbn(), book);
        return true;
    }

    @Override
    public Book getBookById(String bookId) {
        return booksById.get(bookId);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(booksById.values()); // Return all books
    }

    @Override
    public boolean updateBook(Book updatedBook) {
        if (!booksById.containsKey(updatedBook.getBookId())) {
            return false; // Book not found
        }
        // Remove old entry by ISBN if ISBN changed
        Book oldBook = booksById.get(updatedBook.getBookId());
        if (!oldBook.getIsbn().equals(updatedBook.getIsbn())) {
            booksByIsbn.remove(oldBook.getIsbn());
        }

        booksById.put(updatedBook.getBookId(), updatedBook);
        booksByIsbn.put(updatedBook.getIsbn(), updatedBook);
        return true;
    }

    @Override
    public boolean deleteBook(String bookId) {
        Book removedBook = booksById.remove(bookId);
        if (removedBook != null) {
            booksByIsbn.remove(removedBook.getIsbn());
            return true;
        }
        return false;
    }

    @Override
    public void increaseBookQuantity(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            book.setTotalCopies(book.getTotalCopies() + 1); // Also increase total copies if one is added
        }
    }

    @Override
    public void decreaseBookQuantity(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book != null && book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
        }
    }
}