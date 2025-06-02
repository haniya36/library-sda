package com.library.lib.service;

import com.library.lib.dao.FineDao;
import com.library.lib.model.Fine;
import com.library.lib.model.FineTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors; // For stream operations

public class FineService {
    private FineDao fineDao;

    public FineService(FineDao fineDao) {
        this.fineDao = fineDao;
    }

    public FineService() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean assessFine(Fine fine) {
        // Basic validation for the fine itself
        if (fine == null || fine.getFineId() == null || fine.getFineId().isEmpty() ||
            fine.getMemberId() == null || fine.getMemberId().isEmpty() ||
            fine.getBorrowingRecordId() == null || fine.getBorrowingRecordId().isEmpty() ||
            fine.getAmount() <= 0) { // Using getAmount() now
            throw new IllegalArgumentException("Fine details are incomplete or invalid.");
        }

        // You might want to add more sophisticated checks here,
        // e.g., if the member exists, if the borrowing record exists, etc.
        // For simplicity, directly saving for now.

        fineDao.saveFine(fine);
        System.out.println("FineService: Assessed fine " + fine.getFineId() + " for member " + fine.getMemberId());
        return true;
    }

    public boolean payFine(String fineId) {
        Fine fine = fineDao.getFineById(fineId);
        if (fine == null) {
            System.out.println("FineService: Fine with ID " + fineId + " not found.");
            return false;
        }
        if (fine.isPaid()) {
            System.out.println("FineService: Fine with ID " + fineId + " is already paid.");
            return false;
        }

        fine.setPaid(true);
        fine.setFineDate(LocalDate.now()); // Update fine date to payment date if desired, or keep original assessment date
        fineDao.updateFine(fine);
        System.out.println("FineService: Fine " + fineId + " marked as paid.");
        return true;
    }

    public List<Fine> getAllFines() {
        return fineDao.getAllFines();
    }

    public List<Fine> getUnpaidFines() {
        return fineDao.getAllFines().stream()
                       .filter(fine -> !fine.isPaid())
                       .collect(Collectors.toList());
    }

    public List<Fine> getFinesByMemberId(String memberId) {
        return fineDao.getFinesByMemberId(memberId);
    }

    public boolean recordPayment(FineTransaction transaction) {
         if (transaction == null) {
            System.err.println("FineService: Attempted to record a null transaction.");
            return false;
        }

        System.out.println("FineService: Recording payment for Member ID: " + transaction.getMemberId() +
                           ", Book ID: " + (transaction.getBookId() != null ? transaction.getBookId() : "N/A") +
                           ", Amount: " + transaction.getAmount());
        
        
        
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
        
        
    }
}