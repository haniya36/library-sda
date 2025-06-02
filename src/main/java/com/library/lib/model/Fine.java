package com.library.lib.model;

import java.time.LocalDate;
import java.io.Serializable;

public class Fine implements Serializable {
    private String fineId;
    private String memberId;
    private String borrowingRecordId; // Link to the specific borrowing record
    private double amount;
    private boolean paid;
    private LocalDate fineDate;

    public Fine(String fineId, String memberId, String borrowingRecordId, double amount, boolean paid, LocalDate fineDate) {
        this.fineId = fineId;
        this.memberId = memberId;
        this.borrowingRecordId = borrowingRecordId;
        this.amount = amount;
        this.paid = paid;
        this.fineDate = fineDate;
    }

    public Fine(String fineId, String memberId, String bookIsbn, double amount, LocalDate now) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters
    public String getFineId() { return fineId; }
    public String getMemberId() { return memberId; }
    public String getBorrowingRecordId() { return borrowingRecordId; }
    public double getAmount() { return amount; }
    public boolean isPaid() { return paid; }
    public LocalDate getFineDate() { return fineDate; }

    // Setters
    public void setFineId(String fineId) { this.fineId = fineId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaid(boolean paid) { this.paid = paid; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }

    @Override
    public String toString() {
        return "Fine{" +
               "fineId='" + fineId + '\'' +
               ", memberId='" + memberId + '\'' +
               ", borrowingRecordId='" + borrowingRecordId + '\'' +
               ", amount=" + amount +
               ", paid=" + paid +
               ", fineDate=" + fineDate +
               '}';
    }
}