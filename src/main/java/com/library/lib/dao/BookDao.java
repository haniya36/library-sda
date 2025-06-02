// File: com/library/lib/dao/BookDao.java
package com.library.lib.dao;

import com.library.lib.model.Book;
import java.util.List;

public interface BookDao {
    boolean addBook(Book book);
    Book getBookById(String bookId); // Uses bookId
    List<Book> getAllBooks();
    boolean updateBook(Book book);
    boolean deleteBook(String bookId); // Uses bookId
    List<Book> searchBooks(String query);
    // THESE ARE THE CRITICAL ONES TO CHECK - MUST HAVE TWO parameters:
    void decreaseBookQuantity(String bookId, int quantity);
    void increaseBookQuantity(String bookId, int quantity);

    public void decreaseBookQuantity(String bookIsbn);

    public Book getBookByIsbn(String bookIsbn);

    public void increaseBookQuantity(String bookIsbn);
}