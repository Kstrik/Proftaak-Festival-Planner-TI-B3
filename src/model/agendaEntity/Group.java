package model.agendaEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Group {

    private int id;
    private String name;
    private ArrayList<Member> members;
    private ArrayList<Schedule> schedules;
    private boolean isTeacherGroup = false;

    public Group(int id, String name, ArrayList<Member> members, ArrayList<Schedule> schedules, boolean isTeacherGroup) {

        this.id = id;
        this.name = name;
        this.members = members;
        this.schedules = schedules;
        this.isTeacherGroup = isTeacherGroup;
    }

    public Group() {

    }

    // methods
    public boolean containsScheduleItem(ScheduleItem scheduleItem) {

        for (Schedule schedule : this.schedules)
            if (schedule.containsScheduleItem(scheduleItem))
                return true;

        return false;
    }

    public void addMember(Member member) {

        this.members.add(member);
    }

    public void addSchedule(Schedule schedule) {

        this.schedules.add(schedule);
    }

    public Member getPerson(int key) {

        return this.members.get(key);
    }

    public Schedule getScheduleByDate(LocalDateTime date) {

        for (Schedule schedule : this.schedules)
            if (schedule.getDate().equals(date))
                return schedule;

        return null;
    }

    public Schedule getSchedule(int key) {

        return this.schedules.get(key);
    }

    // getters
    public int getId() {

        return this.id;
    }

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
    public void setId(int id) {

        this.id = id;
    }

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
