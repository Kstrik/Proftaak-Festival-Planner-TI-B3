package model.entity;

import java.util.ArrayList;

public class Group {

    private int id;
    private String name;
    private ArrayList<Person> members;
    private ArrayList<Schedule> schedules;
    private boolean isTeacherGroup = false;

    public Group(int id, String name, ArrayList<Person> members, ArrayList<Schedule> schedules, boolean isTeacherGroup) {

        this.id = id;
        this.name = name;
        this.members = members;
        this.schedules = schedules;
        this.isTeacherGroup = isTeacherGroup;
    }

    public Group() {

    }

    // methods
    public boolean containsItem(Item item) {

        for (Schedule schedule : this.schedules)
            if (schedule.containsItem(item))
                return true;

        return false;
    }

    public void addMember(Person person) {

        this.members.add(person);
    }

    public void addSchedule(Schedule schedule) {

        this.schedules.add(schedule);
    }

    public Person getPerson(int key) {

        return this.members.get(key);
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
        group.append("\"id\": \"")             .append(this.id)                  .append("\",\n");
        group.append("\"name\": \"")           .append(this.name)                .append("\",\n");
        group.append("\"members\": ")          .append(this.membersToString())   .append(",\n");
        group.append("\"schedules\": ")        .append(this.schedulesToString()) .append(",\n");
        group.append("\"isTeacherGroup\": \"") .append(this.isTeacherGroup)      .append("\"\n");
        group.append("}");

        return group.toString();
    }

    private String membersToString() {

        StringBuilder members = new StringBuilder();

        members.append("[\n");

        for (int i = 0; i < this.members.size(); i++)
            if (i == (this.members.size() - 1))
                members.append(this.members.get(i).toString()).append("\n");
            else
                members.append(this.members.get(i).toString()).append(",\n");

        members.append("]");

        return members.toString();
    }

    private String schedulesToString() {

        StringBuilder schedules = new StringBuilder();

        schedules.append("[\n");

        for (int i = 0; i < this.schedules.size(); i++)
            if (i == (this.schedules.size() - 1))
                schedules.append(this.schedules.get(i).toString()).append(",\n");
            else
                schedules.append(this.schedules.get(i).toString()).append("\n");

        schedules.append("]");

        return schedules.toString();
    }
}
