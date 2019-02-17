package model.agendaEntity;

import java.time.LocalDateTime;

public class ScheduleItem {

    private long groupID;
    private String name;
    private Member teacher;
    private LocalDateTime start, end;
    private Classroom classroom;

    public ScheduleItem(int groupID, String name, LocalDateTime start, LocalDateTime end, Member teacher, Classroom classroom) {

        this.groupID = groupID;
        this.name = name;
        this.start = start;
        this.end = end;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    public ScheduleItem() {

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
    public long getGroupID() {

        return groupID;
    }

    public String getName() {

        return name;
    }

    public Member getTeacher() {

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
    public void setGroupID(long groupID) {

        this.groupID = groupID;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setTeacher(Member teacher) {

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
}
