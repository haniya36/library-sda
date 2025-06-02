package com.library.lib.dao;

import com.library.lib.model.Fine;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryFineDaoImpl implements FineDao {
    private Map<String, Fine> fines = new HashMap<>();

    public InMemoryFineDaoImpl() {
        // You can pre-populate with some dummy fines if needed
        // fines.put("fine001", new Fine("fine001", "mem002", "brec002", 0.50, false, LocalDate.of(2025, 5, 20)));
    }

    @Override
    public void saveFine(Fine fine) {
        if (fine.getFineId() == null || fine.getFineId().isEmpty()) {
            fine.setFineId(UUID.randomUUID().toString());
        }
        fines.put(fine.getFineId(), fine);
        System.out.println("DEBUG: Saved fine: " + fine.getFineId());
    }

    @Override
    public Fine getFineById(String fineId) {
        return fines.get(fineId);
    }

    @Override
    public List<Fine> getFinesByMemberId(String memberId) {
        List<Fine> memberFines = new ArrayList<>();
        for (Fine fine : fines.values()) {
            if (fine.getMemberId().equals(memberId)) {
                memberFines.add(fine);
            }
        }
        return memberFines;
    }

    @Override
    public List<Fine> getAllFines() {
        return new ArrayList<>(fines.values());
    }

    @Override
    public void updateFine(Fine fine) {
        if (fines.containsKey(fine.getFineId())) {
            fines.put(fine.getFineId(), fine);
            System.out.println("DEBUG: Updated fine: " + fine.getFineId());
        } else {
            System.out.println("DEBUG: Fine " + fine.getFineId() + " not found for update.");
        }
    }

    @Override
    public void removeFine(String fineId) {
        fines.remove(fineId);
        System.out.println("DEBUG: Removed fine: " + fineId);
    }
}