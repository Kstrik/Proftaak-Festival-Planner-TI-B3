package model.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Item {

    private int id;
    private int teacherId;
    private int classroomId;
    private String name;
    private LocalTime start, end;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Item(int id, int teacherId, int classroomId, String name, LocalTime start, LocalTime end) {

        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.teacherId = teacherId;
        this.classroomId = classroomId;
    }

    public Item() {

        this.id = -1;
        this.name = "";
        this.start = LocalTime.now();
        this.end = LocalTime.now().plusSeconds(1);
        this.teacherId = -1;
        this.classroomId = -1;
    }

    // methods
    public double getLessonDouble() {

        return this.getEndDouble() - this.getStartDouble();
    }

    public double getStartDouble() {

        return this.start.getHour() + (((double) this.start.getMinute()) * ((double) 100 / 60) / 100);
    }

    public double getEndDouble() {

        return this.end.getHour() + (((double) this.end.getMinute()) * ((double) 100/60) / 100);
    }

    public String getParsedStart() {

        String string = this.start.getHour() + ":";

        if (Integer.toString(this.start.getMinute()).length() < 2)
            string += "0";

        return string + this.start.getMinute();
    }

    public String getParsedEnd() {

        String string = this.end.getHour() + ":";

        if (Integer.toString(this.end.getMinute()).length() < 2)
            string += "0";

        return string + this.end.getMinute();
    }

    // getters
    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public int getTeacherId() {

        return this.teacherId;
    }

    public LocalTime getStart() {

        return this.start;
    }

    public LocalTime getEnd() {

        return this.end;
    }

    public int getClassroomId() {

        return this.classroomId;
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setTeacherId(int teacherId) {

        this.teacherId = teacherId;
    }

    public void setStart(LocalTime start) {

        this.start = start;
    }

    public void setEnd(LocalTime end) {

        this.end = end;
    }

    public void setClassroomId(int classroomId) {

        this.classroomId = classroomId;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder item = new StringBuilder();

        item.append("{\n");
        item.append("\t\"id\":")          .append(this.id)                                   .append(",\n");
        item.append("\t\"teacherId\":")   .append(this.teacherId)                            .append(",\n");
        item.append("\t\"classroomId\":") .append(this.classroomId)                          .append(",\n");
        item.append("\t\"name\": \"")     .append(this.name)                                 .append("\",\n");
        item.append("\t\"start\":\"")     .append(this.start.format(this.dateTimeFormatter)) .append("\",\n");
        item.append("\t\"end\":\"")       .append(this.end.format(this.dateTimeFormatter))   .append("\"\n");
        item.append("}");

        return item.toString();
    }
}
