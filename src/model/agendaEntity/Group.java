package model.agendaEntity;

import java.util.ArrayList;

public class Group {

    private String name;
    private ArrayList<Member> members;
    private ArrayList<Integer> scheduleItemsIDs;
    private boolean isTeacherGroup = false;

    public Group(String name, ArrayList<Member> members, ArrayList<Integer> scheduleItemsIDs, boolean isTeacherGroup) {

        this.name = name;
        this.members = members;
        this.scheduleItemsIDs = scheduleItemsIDs;
        this.isTeacherGroup = isTeacherGroup;
    }

    public Group() {

    }

    // methods
    public void addMember(Member member) {

        this.members.add(member);
    }

    public void addScheduleItemID(Integer scheduleItemID) {

        this.scheduleItemsIDs.add(scheduleItemID);
    }

    public Member getPerson(int index) {

        return (!this.CanGetIndex(index)) ? null : this.members.get(index);
    }

    private boolean CanGetIndex(int index) {

        return (!(index >= this.members.toArray().length || index < 0));
    }

    // getters
    public String getName() {

        return this.name;
    }

    public ArrayList<Member> getMembers() {

        return this.members;
    }

    public ArrayList<Integer> getScheduleItemIDs() {

        return this.scheduleItemsIDs;
    }

    public boolean isTeacherGroup() {

        return this.isTeacherGroup;
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setMembers(ArrayList<Member> members) {

        this.members = members;
    }

    public void setScheduleItemIDs(ArrayList<Integer> scheduleItemsIDs) {

        this.scheduleItemsIDs = scheduleItemsIDs;
    }

    public void setTeacherGroup(boolean teacherGroup) {

        isTeacherGroup = teacherGroup;
    }
}
