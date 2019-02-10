package agenda;

import java.util.Date;
import java.util.ArrayList;

public class Agenda {
    private ArrayList<Person> studentList;
    private ArrayList<Schedule> schedules;

    public Agenda() {
        this.schedules = new ArrayList<>();
    }

    public void createSchedule(Date date, ArrayList<ScheduleItem> items) {
        Schedule schedule = new Schedule(date, items);
        this.schedules.add(schedule);
    }

    public Schedule getSchedule(int index) {
        return this.schedules.get(index);
    }
}
