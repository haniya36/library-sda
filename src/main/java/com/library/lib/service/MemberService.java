// MemberService.java
package com.library.lib.service;

import com.library.lib.dao.MemberDao; // Make sure this import is correct
import com.library.lib.model.Member;  // Make sure this import is correct

import java.util.List;

public class MemberService {
    private MemberDao memberDao;

    // Constructor to inject the MemberDao implementation
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public boolean registerMember(Member member) {
        // You might add business logic/validation here before calling DAO
        System.out.println("SERVICE: Attempting to register member: " + member.getName());
        return memberDao.addMember(member);
    }

    public Member getMemberById(String memberId) {
        System.out.println("SERVICE: Attempting to get member by ID: " + memberId);
        return memberDao.getMemberById(memberId);
    }

    public List<Member> getAllMembers() {
        System.out.println("SERVICE: Fetching all members.");
        return memberDao.getAllMembers();
    }

    public boolean updateMember(Member member) {
        // You might add business logic/validation here
        System.out.println("SERVICE: Attempting to update member: " + member.getName());
        return memberDao.updateMember(member);
    }

    // This is the missing or incorrect method causing your error
    public boolean removeMember(String memberId) {
        System.out.println("SERVICE: Attempting to remove member with ID: " + memberId);
        // This calls the removeMember method from your InMemoryMemberDaoImpl
        return memberDao.removeMember(memberId);
    }

    public List<Member> searchMembers(String query) {
        System.out.println("SERVICE: Searching members for query: " + query);
        return memberDao.searchMembers(query);
    }

    // You might also have a save method if your DAO has one, or registerMember handles it.
    // public boolean saveMember(Member member) {
    //     System.out.println("SERVICE: Attempting to save member: " + member.getName());
    //     return memberDao.saveMember(member);
    // }
}