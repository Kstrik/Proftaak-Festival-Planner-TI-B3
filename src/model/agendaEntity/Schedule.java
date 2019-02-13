package model.agendaEntity;

import java.util.ArrayList;
import java.util.Date;

public class Schedule {

    private String name;
    private Date date;
    private ArrayList<ScheduleItem> scheduleItems;

    public Schedule(String name, Date date, ArrayList<ScheduleItem> scheduleItems) {

        this.name = name;
        this.date = date;
        this.scheduleItems = scheduleItems;
    }

    public Schedule() {

    }

    // methods
    public void addScheduleItem(ScheduleItem item) {

        this.scheduleItems.add(item);
    }

    public ScheduleItem getScheduleItem(int key) {

        return this.scheduleItems.get(key);
    }

    // getters
    public String getName() {

        return name;
    }

    public Date getDate() {

        return date;
    }

    public ArrayList<ScheduleItem> getScheduleItems() {

        return scheduleItems;
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public void setScheduleItems(ArrayList<ScheduleItem> scheduleItems) {

        this.scheduleItems = scheduleItems;
    }
}
