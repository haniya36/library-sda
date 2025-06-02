package com.library.lib.model;

import java.time.LocalDate;
import java.io.Serializable;

public class BorrowingRecord implements Serializable {
    private String recordId;
    private String memberId;
    private String bookIsbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // Null if not returned
    private double fineAmount;

    public BorrowingRecord(String recordId, String memberId, String bookIsbn,
                           LocalDate borrowDate, LocalDate dueDate,
                           LocalDate returnDate, double fineAmount) {
        this.recordId = recordId;
        this.memberId = memberId;
        this.bookIsbn = bookIsbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    // Getters
    public String getRecordId() { return recordId; }
    public String getMemberId() { return memberId; }
    public String getBookIsbn() { return bookIsbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getFineAmount() { return fineAmount; }

    // Setters (for updating status like return date and fine)
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    @Override
    public String toString() {
        return "BorrowingRecord{" +
               "recordId='" + recordId + '\'' +
               ", memberId='" + memberId + '\'' +
               ", bookIsbn='" + bookIsbn + '\'' +
               ", borrowDate=" + borrowDate +
               ", dueDate=" + dueDate +
               ", returnDate=" + (returnDate != null ? returnDate : "N/A") +
               ", fineAmount=" + fineAmount +
               '}';
    }
}