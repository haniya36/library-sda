package com.library.lib.dao;

import com.library.lib.model.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class should be in its own file: com/library/lib/dao/InMemoryMemberDaoImpl.java
public class InMemoryMemberDaoImpl implements MemberDao {
    private Map<String, Member> members = new HashMap<>();

    public InMemoryMemberDaoImpl() {
        members.put("M001", new Member("M001", "Alice Smith", "alice@example.com", "Student"));
        members.put("M002", new Member("M002", "Bob Johnson", "bob@example.com", "Faculty"));
    }

    @Override
    public boolean addMember(Member member) {
        if (members.containsKey(member.getMemberId())) {
            return false;
        }
        members.put(member.getMemberId(), member);
        System.out.println("DEBUG: Added member: " + member.getName());
        return true;
    }

    @Override
    public Member getMemberById(String memberId) {
        return members.get(memberId);
    }

    @Override
    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    @Override
    public boolean updateMember(Member member) {
        if (members.containsKey(member.getMemberId())) {
            members.put(member.getMemberId(), member);
            System.out.println("DEBUG: Updated member: " + member.getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMember(String memberId) {
        if (members.containsKey(memberId)) {
            members.remove(memberId);
            System.out.println("DEBUG: Removed member with ID: " + memberId);
            return true;
        }
        return false;
    }

    @Override
    public List<Member> searchMembers(String query) {
        List<Member> foundMembers = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        for (Member member : members.values()) {
            if (member.getMemberId().toLowerCase().contains(lowerCaseQuery) ||
                member.getName().toLowerCase().contains(lowerCaseQuery) ||
                (member.getContactInfo() != null && member.getContactInfo().toLowerCase().contains(lowerCaseQuery)) ||
                (member.getMemberType() != null && member.getMemberType().toLowerCase().contains(lowerCaseQuery))) {
                foundMembers.add(member);
            }
        }
        return foundMembers;
    }

    // Fixed: Provide actual implementations for saveMember and removeMember
    @Override
    public boolean saveMember(Member member) {
        // In an in-memory DAO, save can often be treated as add or update
        // This simple implementation checks if it exists, then updates or adds.
        if (members.containsKey(member.getMemberId())) {
            return updateMember(member);
        } else {
            return addBook(member);
        }
    }

    @Override
    public boolean removeMember(String memberId) {
        // Delegate to the existing deleteMember method
        return deleteMember(memberId);
    }

    private boolean addBook(Member member) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}