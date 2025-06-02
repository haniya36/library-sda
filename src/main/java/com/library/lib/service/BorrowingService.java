package com.library.lib.service;

import com.library.lib.dao.BookDao;
import com.library.lib.dao.MemberDao;
import com.library.lib.dao.BorrowingRecordDao;
import com.library.lib.dao.FineDao;
import com.library.lib.model.Book;
import com.library.lib.model.BorrowingRecord;
import com.library.lib.model.Member;
import com.library.lib.model.Fine;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID; // For generating unique IDs

public class BorrowingService {
    private BookDao bookDao;
    private MemberDao memberDao;
    private BorrowingRecordDao borrowingRecordDao;
    private FineDao fineDao;

    // Constructor accepting the DAO interfaces
    public BorrowingService(BookDao bookDao, MemberDao memberDao, BorrowingRecordDao borrowingRecordDao, FineDao fineDao) {
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        this.borrowingRecordDao = borrowingRecordDao;
        this.fineDao = fineDao;
    }

    public boolean issueBook(String memberId, String bookIsbn) {
        // Validation
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be empty.");
        }
        if (bookIsbn == null || bookIsbn.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN cannot be empty.");
        }

        Member member = memberDao.getMemberById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID " + memberId + " not found.");
        }

        Book book = bookDao.getBookByIsbn(bookIsbn);
        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + bookIsbn + " not found.");
        }
        if (!book.isAvailable()) { // Assuming Book has an isAvailable() method
            throw new IllegalArgumentException("Book '" + book.getTitle() + "' is currently not available.");
        }

        // Create borrowing record
        String recordId = UUID.randomUUID().toString(); // Generate unique ID
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14); // Example: 14 days borrowing period

        BorrowingRecord newRecord = new BorrowingRecord(recordId, memberId, bookIsbn, borrowDate, dueDate, null, 0.0);

        // Update book availability/quantity
        bookDao.decreaseBookQuantity(bookIsbn); // Mark as unavailable or decrease count

        // Save the record
        borrowingRecordDao.saveBorrowingRecord(newRecord);

        System.out.println("DEBUG: Book '" + book.getTitle() + "' issued to member '" + member.getName() + "'. Record ID: " + recordId);
        return true;
    }

    public boolean returnBook(String recordId) {
        if (recordId == null || recordId.trim().isEmpty()) {
            throw new IllegalArgumentException("Record ID cannot be empty.");
        }

        BorrowingRecord record = borrowingRecordDao.getBorrowingRecordById(recordId);
        if (record == null) {
            throw new IllegalArgumentException("Borrowing record with ID " + recordId + " not found.");
        }
        if (record.getReturnDate() != null) {
            throw new IllegalArgumentException("Book from record " + recordId + " has already been returned.");
        }

        record.setReturnDate(LocalDate.now());

        // Calculate fine if overdue
        double fineAmount = 0.0;
        if (record.getReturnDate().isAfter(record.getDueDate())) {
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());
            fineAmount = overdueDays * 0.50; // Example: $0.50 per day overdue
            record.setFineAmount(fineAmount);

            // Create and save a fine record
            String fineId = UUID.randomUUID().toString();
            Fine newFine = new Fine(fineId, record.getMemberId(), recordId, fineAmount, false, LocalDate.now()); // Assuming Fine class constructor
            fineDao.saveFine(newFine);
            System.out.println("DEBUG: Fine of $" + fineAmount + " added for record " + recordId + ".");
        }

        // Update book availability/quantity
        bookDao.increaseBookQuantity(record.getBookIsbn()); // Mark as available or increase count

        borrowingRecordDao.updateBorrowingRecord(record); // Update the record with return date and fine

        System.out.println("DEBUG: Book for record " + recordId + " returned. Fine: $" + fineAmount);
        return true;
    }

    public List<BorrowingRecord> getAllBorrowingRecords() {
        System.out.println("DEBUG: Retrieving all borrowing records.");
        return borrowingRecordDao.getAllBorrowingRecords();
    }
}