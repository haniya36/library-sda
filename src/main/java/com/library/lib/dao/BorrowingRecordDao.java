package com.library.lib.dao;

import com.library.lib.model.BorrowingRecord;
import java.util.List;

public interface BorrowingRecordDao {
    void saveBorrowingRecord(BorrowingRecord record);
    BorrowingRecord getBorrowingRecordById(String recordId);
    List<BorrowingRecord> getAllBorrowingRecords();
    void updateBorrowingRecord(BorrowingRecord record);
    void removeBorrowingRecord(String recordId);
    // You might add methods like getRecordsByMemberId, getRecordsByBookIsbn if needed
}