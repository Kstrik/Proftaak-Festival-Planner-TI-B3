package model.agendaEntity_old;

public class LessonEntity {

    private String name;      // for instance: Wiskunde
    private String group;     // for instance: A6
    private String teacher;   // for instance: Pieter Kop Jansen
    private String classRoom; // for instance: LD004
    private double startTime; // for instance: 12.30
    private double endTime;   // for instance: 14.00

    // method
    public double getLessonLength() {

        return (this.getEndTime() - this.getStartTime());
    }

    public String getParsedStartTime() {

        return ((int) this.getStartTime()) + ":" + ((int) ((this.getStartTime() % 1) * 60));
    }

    public String getParsedEndTime() {

        return ((int) this.getEndTime()) + ":" + ((int) ((this.getEndTime() % 1) * 60));
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setGroup(String group) {

        this.group = group;
    }

    public void setTeacher(String teacher) {

        this.teacher = teacher;
    }

    public void setClassRoom(String classRoom) {

        this.classRoom = classRoom;
    }

    public void setStartTime(double startTime) {

        this.startTime = startTime;
    }

    public void setEndTime(double endTime) {

        this.endTime = endTime;
    }

    // getters
    public String getName() {

        return name;
    }

    public String getGroup() {

        return group;
    }

    public String getTeacher() {

        return teacher;
    }

    public String getClassRoom() {

        return classRoom;
    }

    public double getStartTime() {

        return startTime;
    }

    public double getEndTime() {

        return endTime;
    }

    // overrides
    @Override
    public String toString() {

        return
            "\t name: " + this.getName() +
            "\n\t group: " + this.getGroup() +
            "\n\t teacher: " + this.getTeacher() +
            "\n\t classRoom: " + this.getClassRoom() +
            "\n\t startTime: " + this.getStartTime() +
            "\n\t endTime: " + this.getEndTime()
        ;
    }
}
