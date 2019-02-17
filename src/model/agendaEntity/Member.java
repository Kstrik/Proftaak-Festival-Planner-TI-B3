package model.agendaEntity;

public class Member {

    private String name;
    private String gender;
    private long memberID;
    private boolean isTeacher;

    public Member(String name, String gender, int memberID, boolean isTeacher) {

        this.name = name;
        this.gender = gender;
        this.memberID = memberID;
        this.isTeacher = isTeacher;
    }

    public Member() {

    }

    // getters
    public long getMemberID() {

        return this.memberID;
    }

    public String getName() {

        return this.name;
    }

    public String getGender() {

        return this.gender;
    }

    public boolean isTeacher() {

        return isTeacher;
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public void setMemberID(long memberID) {

        this.memberID = memberID;
    }

    public void setIsTeacher(boolean isTeacher) {

        this.isTeacher = isTeacher;
    }
}
