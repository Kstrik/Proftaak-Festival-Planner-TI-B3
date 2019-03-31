package model.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Group {

    private int id;
    private String name;
    private ArrayList<Person> members;
    private ArrayList<Schedule> schedules;
    private boolean isTeacherGroup;

    public Group(int id, String name, ArrayList<Person> members, ArrayList<Schedule> schedules, boolean isTeacherGroup) {

        this.id = id;
        this.name = name;
        this.members = members;
        this.schedules = schedules;
        this.isTeacherGroup = isTeacherGroup;
    }

    public Group() {

        this.id = -1;
        this.name = "";
        this.members = new ArrayList<>();
        this.schedules = new ArrayList<>();
        this.isTeacherGroup = false;
    }

    // methods
    public boolean containsItem(Item item) {

        for (Schedule schedule : this.schedules)
            if (schedule.containsItem(item))
                return true;

        return false;
    }

    public Schedule getScheduleByItem(Item item) {

        for (Schedule schedule : this.schedules)
            if (schedule.getItems().contains(item))
                return schedule;

        return new Schedule();
    }

    public Schedule getScheduleByDate(LocalDate date) {

        for (Schedule schedule : this.schedules)
            if (schedule.getDate() == date)
                return schedule;

        return new Schedule();
    }

    public ArrayList<LocalDate> getScheduleDates() {

        ArrayList<LocalDate> dates = new ArrayList<>();

        for (Schedule schedule : this.schedules)
            if (!dates.contains(schedule.getDate()))
                dates.add(schedule.getDate());

        return dates;
    }

    // getters
    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public ArrayList<Person> getMembers() {

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

    public void setMembers(ArrayList<Person> members) {

        this.members = members;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {

        this.schedules = schedules;
    }

    public void setTeacherGroup(boolean teacherGroup) {

        isTeacherGroup = teacherGroup;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder group = new StringBuilder();

        group.append("{\n");
        group.append("\t\"id\": ")             .append(this.id)                  .append(",\n");
        group.append("\t\"name\": \"")         .append(this.name)                .append("\",\n");
        group.append("\t\"members\": ")        .append(this.membersToString())   .append(",\n");
        group.append("\t\"schedules\": ")      .append(this.schedulesToString()) .append(",\n");
        group.append("\t\"isTeacherGroup\": ") .append(this.isTeacherGroup)      .append("\n");
        group.append("}");

        return group.toString();
    }

    private String membersToString() {

        StringBuilder members = new StringBuilder();

        members.append("[\n");
        for (int i = 0; i < this.members.size(); i++)
            members.append(this.members.get(i).toString()).append(i == (this.members.size() - 1) ? "" : ",\n");

        return members.toString().replace("\n", "\n\t\t") + "\n\t]";
    }

    private String schedulesToString() {

        StringBuilder schedules = new StringBuilder();

        schedules.append("[\n");
        for (int i = 0; i < this.schedules.size(); i++)
            schedules.append(this.schedules.get(i).toString()).append(i == (this.schedules.size() - 1) ? "" : ",\n");

        return schedules.toString().replace("\n", "\n\t\t") + "\n\t]";
    }
}
