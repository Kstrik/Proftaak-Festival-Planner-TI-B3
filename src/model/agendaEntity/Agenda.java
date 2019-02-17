package model.agendaEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Agenda {

    private String name;
    private ArrayList<Group> groups;

    public Agenda() {

    }

    // methods
    public Schedule getFirstSchedule() {

        return this.getScheduleByDate(this.getScheduleDates().get(0));
    }

    public Schedule getScheduleByDate(LocalDateTime date) {

        Schedule dateSchedule = new Schedule(date);

        for (Group group : this.groups)
            for (Schedule schedule : group.getSchedules())
                if (schedule.getDate().isEqual(date))
                    dateSchedule.addScheduleItems(schedule.getScheduleItems());

        return dateSchedule;
    }

    public ArrayList<LocalDateTime> getScheduleDates() {

        ArrayList<LocalDateTime> dates = new ArrayList<>();

        for (Group group : this.groups)
            for (Schedule schedule : group.getSchedules())
                if (!dates.contains(schedule.getDate()))
                    dates.add(schedule.getDate());

        return dates;
    }

    public void addGroup(Group group) {

        this.groups.add(group);
    }

    public Group getGroup(int key) {

        return this.groups.get(key);
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setGroups(ArrayList<Group> groups) {

        this.groups = groups;
    }

    // getters
    public String getName() {

        return this.name;
    }

    public ArrayList<Group> getGroups() {

        return this.groups;
    }
}
