package model.agendaEntity;

import java.util.Date;
import java.util.ArrayList;

public class Agenda {

    private String name;
    private ArrayList<Group> groups;
    private ArrayList<Schedule> schedules;

    public Agenda() {

        this.schedules = new ArrayList<>();
    }

    // methods
    public ArrayList<Date> getScheduleDates() {

        ArrayList<Date> dates = new ArrayList<>();

        for (Schedule schedule : this.schedules)
            if (!dates.contains(schedule.getDate()))
                dates.add(schedule.getDate());

        return dates;
    }

    public void addSchedule(Schedule schedule) {

        this.schedules.add(schedule);
    }

    public void addGroup(Group group) {

        this.groups.add(group);
    }

    public Schedule getschedule(int key) {

        return this.schedules.get(key);
    }

    public Group getGroup(int key) {

        return this.groups.get(key);
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {

        this.schedules = schedules;
    }

    public void setGroups(ArrayList<Group> groups) {

        this.groups = groups;
    }

    // getters
    public String getName() {

        return this.name;
    }

    public ArrayList<Schedule> getSchedules() {

        return this.schedules;
    }

    public ArrayList<Group> getGroups() {

        return this.groups;
    }
}
