/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.lib.model;


    

public class FineTransaction {
    private String memberId;
    private String bookId; // Can be null if fine is not specific to a book
    private double amount;

    public FineTransaction(String memberId, String bookId, double amount) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.amount = amount;
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getBookId() { return bookId; }
    public double getAmount() { return amount; }
}
