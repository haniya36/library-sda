// File: com/library/lib/service/BookService.java
package com.library.lib.service;

import com.library.lib.dao.BookDao;
import com.library.lib.model.Book;

import java.util.List;

public class BookService {
    private BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public boolean addBook(Book book) {
        System.out.println("SERVICE: Attempting to add book: " + book.getTitle());
        return bookDao.addBook(book);
    }

    public Book getBookById(String bookId) { // Changed method name
        System.out.println("SERVICE: Attempting to get book by ID: " + bookId);
        return bookDao.getBookById(bookId); // Changed method call
    }

    public List<Book> getAllBooks() {
        System.out.println("SERVICE: Fetching all books.");
        return bookDao.getAllBooks();
    }

    public boolean updateBook(Book book) {
        System.out.println("SERVICE: Attempting to update book: " + book.getTitle());
        return bookDao.updateBook(book);
    }

    public boolean deleteBook(String bookId) { // Changed method name
        System.out.println("SERVICE: Attempting to delete book with ID: " + bookId);
        return bookDao.deleteBook(bookId); // Changed method call
    }

    public List<Book> searchBooks(String query) {
        System.out.println("SERVICE: Searching books for query: " + query);
        return bookDao.searchBooks(query);
    }

    public Book getBookByIsbn(String bnToSearch) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}