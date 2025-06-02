package com.library.lib.dao;

import com.library.lib.model.BorrowingRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryBorrowingRecordDaoImpl implements BorrowingRecordDao {
    private Map<String, BorrowingRecord> records = new HashMap<>();

    public InMemoryBorrowingRecordDaoImpl() {
        // You can pre-populate with some dummy records if needed
        // For testing initial data:
        records.put("brec001", new BorrowingRecord("brec001", "mem001", "978-0321765723", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 15), null, 0.0));
        records.put("brec002", new BorrowingRecord("brec002", "mem002", "978-0134685991", LocalDate.of(2025, 5, 5), LocalDate.of(2025, 5, 19), LocalDate.of(2025, 5, 20), 0.50)); // overdue
        records.put("brec003", new BorrowingRecord("brec003", "mem001", "978-1234567890", LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 24), null, 0.0));
    }

    @Override
    public void saveBorrowingRecord(BorrowingRecord record) {
        if (record.getRecordId() == null || record.getRecordId().isEmpty()) {
            record.setRecordId(UUID.randomUUID().toString());
        }
        records.put(record.getRecordId(), record);
        System.out.println("DEBUG: Saved borrowing record: " + record.getRecordId());
    }

    @Override
    public BorrowingRecord getBorrowingRecordById(String recordId) {
        return records.get(recordId);
    }

    @Override
    public List<BorrowingRecord> getAllBorrowingRecords() {
        return new ArrayList<>(records.values());
    }

    @Override
    public void updateBorrowingRecord(BorrowingRecord record) {
        if (records.containsKey(record.getRecordId())) {
            records.put(record.getRecordId(), record);
            System.out.println("DEBUG: Updated borrowing record: " + record.getRecordId());
        } else {
            System.out.println("DEBUG: Borrowing record " + record.getRecordId() + " not found for update.");
        }
    }

    @Override
    public void removeBorrowingRecord(String recordId) {
        records.remove(recordId);
        System.out.println("DEBUG: Removed borrowing record: " + recordId);
    }
}