package model.agendaEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Schedule {

    private LocalDateTime date;
    private ArrayList<ScheduleItem> scheduleItems;

    public Schedule(LocalDateTime date, ArrayList<ScheduleItem> scheduleItems) {

        this.date = date;
        this.scheduleItems = scheduleItems;
    }

    public Schedule(LocalDateTime date) {

        this.date = date;
        this.scheduleItems = new ArrayList<>();
    }

    public Schedule() {

    }

    // methods
    public void addScheduleItems(ArrayList<ScheduleItem> scheduleItems) {

        this.scheduleItems.addAll(scheduleItems);
    }

    public void addScheduleItem(ScheduleItem item) {

        this.scheduleItems.add(item);
    }

    public LocalDateTime getScheduleStart() {

        LocalDateTime time = this.scheduleItems.get(0).getStart();

        for (ScheduleItem scheduleItem : this.scheduleItems)
            if (time.isAfter(scheduleItem.getStart()))
                time = scheduleItem.getStart();

        return time;
    }

    public LocalDateTime getScheduleEnd() {

        LocalDateTime time = this.scheduleItems.get(0).getEnd();

        for (ScheduleItem scheduleItem : this.scheduleItems)
            if (time.isBefore(scheduleItem.getEnd()))
                time = scheduleItem.getEnd();

        return time;
    }

    public int getScheduleLength() {

        return (getScheduleEnd().getHour() - getScheduleStart().getHour());
    }

    public ScheduleItem getScheduleItem(int key) {

        return this.scheduleItems.get(key);
    }

    public int getAmountOfScheduleItems() {

        return this.scheduleItems.size();
    }

    public int getAmountOfClassrooms() {

        return getAllClassrooms().size();
    }

    public ArrayList<Classroom> getAllClassrooms() {

        ArrayList<Classroom> classrooms = new ArrayList<>();

        for(ScheduleItem scheduleItem : this.scheduleItems)
            if (!classrooms.contains(scheduleItem.getClassroom()))
                classrooms.add(scheduleItem.getClassroom());

        return classrooms;
    }

    public int getClassRoomKey(Classroom classroom) {

        return this.getAllClassrooms().indexOf(classroom);
    }

    // getters
    public LocalDateTime getDate() {

        return date;
    }

    public ArrayList<ScheduleItem> getScheduleItems() {

        return scheduleItems;
    }

    // setters
    public void setDate(LocalDateTime date) {

        this.date = date;
    }

    public void setScheduleItems(ArrayList<ScheduleItem> scheduleItems) {

        this.scheduleItems = scheduleItems;
    }
}
