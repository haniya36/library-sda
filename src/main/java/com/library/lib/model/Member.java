package com.library.lib.model;

public class Member {
    private String memberId;
    private String name;
    private String contactInfo; // Assuming this is email or phone
    private String memberType; // e.g., "Student", "Faculty"

    public Member(String memberId, String name, String contactInfo, String memberType) {
        this.memberId = memberId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.memberType = memberType;
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getContactInfo() { return contactInfo; }
    public String getMemberType() { return memberType; }

    // Setters
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public void setName(String name) { this.name = name; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public void setMemberType(String memberType) { this.memberType = memberType; }

    @Override
    public String toString() {
        return "Member{" +
               "memberId='" + memberId + '\'' +
               ", name='" + name + '\'' +
               ", contactInfo='" + contactInfo + '\'' +
               ", memberType='" + memberType + '\'' +
               '}';
    }
}