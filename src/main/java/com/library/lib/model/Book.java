// File: com/library/lib/model/Book.java
package com.library.lib.model;

public class Book {
    private String bookId; // Unique identifier for the book in the system
    private String title;
    private String author;
    private String isbn;   // Standard ISBN for the book
    private String category;
    private int totalCopies;
    private int availableCopies;

    // Constructor with 7 arguments (bookId, title, author, isbn, category, totalCopies, availableCopies)
    public Book(String bookId, String title, String author, String isbn, String category, int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    // Getters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    // Setters (for properties that can be updated)
    public void setBookId(String bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setCategory(String category) { this.category = category; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    @Override
    public String toString() {
        return "Book{" +
               "bookId='" + bookId + '\'' +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", isbn='" + isbn + '\'' +
               ", category='" + category + '\'' +
               ", totalCopies=" + totalCopies +
               ", availableCopies=" + availableCopies +
               '}';
    }

    public boolean isAvailable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}