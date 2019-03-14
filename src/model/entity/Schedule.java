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

    public Schedule() {

        this.id = -1;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getScheduleStart() {

        if (this.items.size() == 0)
            return LocalDateTime.now();

        LocalDateTime time = this.items.get(0).getStart();

        for (Item item : this.items)
            if (time.isAfter(item.getStart()))
                time = item.getStart();

        return time;
    }

    public LocalDateTime getScheduleEnd() {

        if (this.items.size() == 0)
            return LocalDateTime.now().plusSeconds(1);

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

        StringBuilder schedule = new StringBuilder();

        schedule.append("{\n");
        schedule.append("\"id\": \"")    .append(this.id)              .append("\",\n");
        schedule.append("\"date\": \"")  .append(this.date.toString()) .append("\",\n");
        schedule.append("\"Items\": \"") .append(this.ItemsToString()) .append("\"\n");
        schedule.append("}");

        return schedule.toString();
    }

    private String ItemsToString() {

        StringBuilder items = new StringBuilder();

        items.append("[\n");

        for (int i = 0; i < this.items.size(); i++)
            if (i == (this.items.size() - 1))
                items.append(this.items.get(i).toString()).append("\n");
            else
                items.append(this.items.get(i).toString()).append(",\n");

        items.append("]");

        return items.toString();
    }
}
