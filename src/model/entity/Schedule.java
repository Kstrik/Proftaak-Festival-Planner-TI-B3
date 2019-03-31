package model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    private int id;
    private LocalDate date;
    private ArrayList<Item> items;

    public Schedule(int id, LocalDate date, ArrayList<Item> items) {

        this.id = id;
        this.date = date;
        this.items = items;
    }

    public Schedule() {

        this.id = -1;
        this.date = LocalDate.now();
        this.items = new ArrayList<>();
    }

    // methods
    public boolean overlaps(Item overlappedItem) {

        for (Item item : this.items)
            if (item.getClassroomId() == overlappedItem.getClassroomId())
                if (overlappedItem.getStart().isAfter(item.getStart()) &&
                    overlappedItem.getStart().isBefore(item.getEnd()) ||
                    overlappedItem.getEnd().isAfter(item.getStart()) &&
                    overlappedItem.getEnd().isBefore(item.getEnd()))
                        return true;


        return false;
    }

    public boolean containsItem(Item searchedItem) {

        for (Item item : this.items)
            if (item.getId() == searchedItem.getId())
                return true;
        
        return false;
    }

    public LocalTime getScheduleStart() {

        if (this.items.size() == 0)
            return LocalTime.now();

        LocalTime time = this.items.get(0).getStart();

        for (Item item : this.items)
            if (time.isAfter(item.getStart()))
                time = item.getStart();

        return time;
    }

    public LocalTime getScheduleEnd() {

        if (this.items.size() == 0)
            return LocalTime.now().plusSeconds(1);

        LocalTime time = this.items.get(0).getEnd();

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

    // getters
    public int getId() {

        return this.id;
    }

    public LocalDate getDate() {

        return date;
    }

    public ArrayList<Item> getItems() {

        return items;
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setDate(LocalDate date) {

        this.date = date;
    }

    public void setItems(ArrayList<Item> items) {

        this.items = items;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder schedule = new StringBuilder();

        schedule.append("{\n");
        schedule.append("\t\"id\": \"")    .append(this.id)              .append("\",\n");
        schedule.append("\t\"date\": \"")  .append(this.date.toString()) .append("\",\n");
        schedule.append("\t\"Items\": ")   .append(this.ItemsToString()) .append("\n");
        schedule.append("}");

        return schedule.toString();
    }

    private String ItemsToString() {

        StringBuilder items = new StringBuilder();

        items.append("[\n");
        for (int i = 0; i < this.items.size(); i++)
            items.append(this.items.get(i).toString()).append(i == (this.items.size() - 1) ? "" : ",\n");

        return items.toString().replace("\n", "\n\t\t") + "\n\t]";
    }
}
