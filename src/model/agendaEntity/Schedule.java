package model.agendaEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Schedule {

    private int id;
    private LocalDateTime date;
    private ArrayList<ScheduleItem> scheduleItems;

    public Schedule(int id, LocalDateTime date, ArrayList<ScheduleItem> scheduleItems) {

        this.id = id;
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

    public void addScheduleItem(ScheduleItem scheduleItem) {

        this.scheduleItems.add(scheduleItem);
    }

    public boolean containsScheduleItem(ScheduleItem scheduleItem) {

        return (this.scheduleItems.contains(scheduleItem));
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

    public ArrayList<Member> getAllTeachers() {

        ArrayList<Member> teachers = new ArrayList<>();

        for (ScheduleItem scheduleItem : this.scheduleItems)
            if (!teachers.contains(scheduleItem.getTeacher()) && scheduleItem.getTeacher().isTeacher())
                teachers.add(scheduleItem.getTeacher());

        return teachers;
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
    public int getId() {

        return this.id;
    }

    public LocalDateTime getDate() {

        return date;
    }

    public ArrayList<ScheduleItem> getScheduleItems() {

        return scheduleItems;
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setDate(LocalDateTime date) {

        this.date = date;
    }

    public void setScheduleItems(ArrayList<ScheduleItem> scheduleItems) {

        this.scheduleItems = scheduleItems;
    }
}
