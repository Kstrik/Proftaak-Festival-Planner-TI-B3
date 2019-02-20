package model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Schedule {

    private int id;
    private LocalDateTime date;
    private ArrayList<Item> items;

    public Schedule(int id, LocalDateTime date, ArrayList<Item> items) {

        this.id = id;
        this.date = date;
        this.items = items;
    }

    public Schedule(int id, LocalDateTime date) {

        this.id = id;
        this.date = date;
        this.items = new ArrayList<>();
    }

    public Schedule() {

    }

    // methods
    public void addItems(ArrayList<Item> items) {

        this.items.addAll(items);
    }

    public void addItem(Item item) {

        this.items.add(item);
    }

    public boolean containsItem(Item searchedItem) {

        for (Item item : this.items)
            if (item.getId() == searchedItem.getId())
                return true;
        
        return false;
    }

    public LocalDateTime getScheduleStart() {

        LocalDateTime time = this.items.get(0).getStart();

        for (Item item : this.items)
            if (time.isAfter(item.getStart()))
                time = item.getStart();

        return time;
    }

    public LocalDateTime getScheduleEnd() {

        LocalDateTime time = this.items.get(0).getEnd();

        for (Item item : this.items)
            if (time.isBefore(item.getEnd()))
                time = item.getEnd();

        return time;
    }

    public int getScheduleLength() {

        return (getScheduleEnd().getHour() - getScheduleStart().getHour());
    }

    public Item getItem(int key) {

        return this.items.get(key);
    }

    public int getAmountOfItems() {

        return this.items.size();
    }

    public ArrayList<Person> getAllTeachers() {

        ArrayList<Person> teachers = new ArrayList<>();

        for (Item item : this.items)
            if (!teachers.contains(item.getTeacher()) && item.getTeacher().isTeacher())
                teachers.add(item.getTeacher());

        return teachers;
    }

    public int getAmountOfClassrooms() {

        return getAllClassrooms().size();
    }

    public ArrayList<Classroom> getAllClassrooms() {

        ArrayList<Classroom> classrooms = new ArrayList<>();

        for(Item item : this.items)
            if (!classrooms.contains(item.getClassroom()))
                classrooms.add(item.getClassroom());

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

    public ArrayList<Item> getItems() {

        return items;
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setDate(LocalDateTime date) {

        this.date = date;
    }

    public void setItems(ArrayList<Item> items) {

        this.items = items;
    }

    // toString
    @Override
    public String toString() {

        return
            "{\n"+
                "\"id\": \"" + this.id + "\",\n" +
                "\"date\": \"" + this.date.toString() + "\",\n" +
                "\"Items\": \"" + this.ItemsToString() + "\"\n" +
            "}"
        ;
    }

    private String ItemsToString() {

        StringBuilder Items = new StringBuilder();

        for (int i = 0; i < this.items.size(); i++)
            if (i == (this.items.size() - 1))
                Items.append(this.items.get(i).toString()).append("\n");
            else
                Items.append(this.items.get(i).toString()).append(",\n");

        return
            "[" +
                Items.toString() +
            "]"
        ;
    }
}
