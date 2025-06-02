package com.library.lib.dao;

import com.library.lib.model.Fine;
import java.util.List;

public interface FineDao {
    void saveFine(Fine fine);
    Fine getFineById(String fineId);
    List<Fine> getFinesByMemberId(String memberId); // To get all fines for a member
    List<Fine> getAllFines();
    void updateFine(Fine fine);
    void removeFine(String fineId);
}