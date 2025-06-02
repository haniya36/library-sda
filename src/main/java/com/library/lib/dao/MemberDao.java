package com.library.lib.dao;

import com.library.lib.model.Member;
import java.util.List;

public interface MemberDao {
    boolean addMember(Member member);
    Member getMemberById(String memberId);
    List<Member> getAllMembers();
    boolean updateMember(Member member);
    boolean deleteMember(String memberId); // This is likely the one used by removeMember in service
    List<Member> searchMembers(String query);

    // Ensure these match your MemberService/GUI expectations
    boolean saveMember(Member member); // Declared in your Impl, must be here
    boolean removeMember(String memberId); // Declared in your Impl, must be here
}