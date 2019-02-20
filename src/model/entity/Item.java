package model.entity;

import java.time.LocalDateTime;

public class Item {

    private int id;
    private String name;
    private Person teacher;
    private LocalDateTime start, end;
    private Classroom classroom;

    public Item(int id, String name, LocalDateTime start, LocalDateTime end, Person teacher, Classroom classroom) {

        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    public Item() {

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

    public String getString() {

        return
            this.name + " in " +
            this.classroom.getName() + "\nTeacher: " +
            this.teacher.getName() + "\n" +
            this.getParsedStart() + " - " +
            this.getParsedEnd()
        ;
    }

    public String getParsedStart() {

        String string = this.start.getHour() + ":";

        if (Integer.toString(this.start.getMinute()).length() < 2) string += "0";

        return string + this.start.getMinute();
    }

    public String getParsedEnd() {

        String string = this.end.getHour() + ":";

        if (Integer.toString(this.end.getMinute()).length() < 2) string += "0";

        return string + this.end.getMinute();
    }

    // getters
    public int getId() {

        return this.id;
    }

    public String getName() {

        return name;
    }

    public Person getTeacher() {

        return teacher;
    }

    public LocalDateTime getStart() {

        return start;
    }

    public LocalDateTime getEnd() {

        return end;
    }

    public Classroom getClassroom() {

        return classroom;
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setTeacher(Person teacher) {

        this.teacher = teacher;
    }

    public void setStart(LocalDateTime start) {

        this.start = start;
    }

    public void setEnd(LocalDateTime end) {

        this.end = end;
    }

    public void setClassroom(Classroom classroom) {

        this.classroom = classroom;
    }

    // toString
    @Override
    public String toString() {

        return
            "{\n" +
                "\"id\": \"" + this.id + "\",\n" +
                "\"name\": \"" + this.name + "\",\n" +
                "\"classroom\": \"" + this.classroom.toString() + "\",\n" +
                "\"start\": \"" + this.start.toString() + "\",\n" +
                "\"end\": \"" + this.end.toString() + "\",\n" +
                "\"teacher\": \"" + this.teacher.toString() + "\"\n" +
            "}"
        ;
    }
}
