package model.agendaEntity;

import java.sql.Time;

public class ScheduleItem {

    private int groupID;
    private String name;
    private Member teacher;
    private Time start, end;
    private Classroom classroom;

    public ScheduleItem(int groupID, String name, Time start, Time end, Member teacher, Classroom classroom) {

        this.groupID = groupID;
        this.name = name;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    public ScheduleItem() {

    }

    // getters
    public int getGroupID() {

        return groupID;
    }

    public String getName() {

        return name;
    }

    public Member getTeacher() {

        return teacher;
    }

    public Time getStart() {

        return start;
    }

    public Time getEnd() {

        return end;
    }

    public Classroom getClassroom() {

        return classroom;
    }

    // setters
    public void setGroupID(int groupID) {

        this.groupID = groupID;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setTeacher(Member teacher) {

        this.teacher = teacher;
    }

    public void setStart(Time start) {

        this.start = start;
    }

    public void setEnd(Time end) {

        this.end = end;
    }

    public void setClassroom(Classroom classroom) {

        this.classroom = classroom;
    }
}
