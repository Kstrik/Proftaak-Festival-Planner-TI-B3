package model.agendaEntity;

import java.util.ArrayList;

public class Group {

    private String name;
    private ArrayList<Member> members;
    private ArrayList<Schedule> schedules;
    private boolean isTeacherGroup = false;

    public Group(String name, ArrayList<Member> members, ArrayList<Schedule> schedules, boolean isTeacherGroup) {

        this.name = name;
        this.members = members;
        this.schedules = schedules;
        this.isTeacherGroup = isTeacherGroup;
    }

    public Group() {

    }

    // methods
    public void addMember(Member member) {

        this.members.add(member);
    }

    public void addScheduleItemID(Schedule schedule) {

        this.schedules.add(schedule);
    }

    public Member getPerson(int index) {

        return (!this.CanGetIndex(index)) ? null : this.members.get(index);
    }

    public Schedule getSchedule(int key) {

        return this.schedules.get(key);
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

    public ArrayList<Schedule> getSchedules() {

        return this.schedules;
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

    public void setSchedules(ArrayList<Schedule> schedules) {

        this.schedules = schedules;
    }

    public void setTeacherGroup(boolean teacherGroup) {

        isTeacherGroup = teacherGroup;
    }
}
