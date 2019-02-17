package model.agendaEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Agenda {

    private int id;
    private String name;
    private ArrayList<Group> groups;

    public Agenda(int id, String name, ArrayList<Group> groups) {

        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public Agenda() {

    }

    // methods
    public Schedule getFirstSchedule() {

        return this.getScheduleByDate(this.getScheduleDates().get(0));
    }

    public ArrayList<Schedule> getAllSchedules() {

        ArrayList<Schedule> schedules = new ArrayList<>();

        for (Group group : this.groups)
            schedules.addAll(group.getSchedules());

        return schedules;
    }

    public Schedule getScheduleByDate(LocalDateTime date) {

        Schedule dateSchedule = new Schedule(date);

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.getDate().isEqual(date))
                dateSchedule.addScheduleItems(schedule.getScheduleItems());

        return dateSchedule;
    }

    public LocalDateTime getDateOfScheduleItem(ScheduleItem scheduleItem) {

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.containsScheduleItem(scheduleItem))
                return schedule.getDate();

        return null;
    }

    public ArrayList<LocalDateTime> getScheduleDates() {

        ArrayList<LocalDateTime> dates = new ArrayList<>();

        for (Schedule schedule : this.getAllSchedules())
            if (!dates.contains(schedule.getDate()))
                dates.add(schedule.getDate());

        return dates;
    }

    public ArrayList<Classroom> getAllClassrooms() {

        ArrayList<Classroom> classrooms = new ArrayList<>();

        for (Schedule schedule : this.getAllSchedules())
            for (Classroom classroom : schedule.getAllClassrooms())
                if (!classrooms.contains(classroom))
                    classrooms.add(classroom);

        return classrooms;
    }

    public ArrayList<String> getAllClassroomNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Classroom classroom : this.getAllClassrooms())
            if (!names.contains(classroom.getName()))
                names.add(classroom.getName());

        return names;
    }

    public Classroom getClassroomByName(String name) {

        for (Classroom classroom : this.getAllClassrooms())
            if (classroom.getName().equals(name))
                return classroom;

        return null;
    }

    public ArrayList<Member> getAllTeachers() {

        ArrayList<Member> teachers = new ArrayList<>();

        for (Schedule schedule : this.getAllSchedules())
            for (Member teacher : schedule.getAllTeachers())
                if (!teachers.contains(teacher))
                    teachers.add(teacher);

        return teachers;
    }

    public ArrayList<String> getAllTeacherNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Member member : this.getAllTeachers())
            if (!names.contains(member.getName()))
                names.add(member.getName());

        return names;
    }

    public Member getTeacherByName(String name) {

        for (Member teacher : this.getAllTeachers())
            if (teacher.getName().equals(name))
                return teacher;

        return null;
    }

    public String getGroupNameOfScheduleItem(ScheduleItem scheduleItem) {

        for (Group group : this.groups)
            if (group.containsScheduleItem(scheduleItem))
                return group.getName();

        return "";
    }

    public void removeScheduleItem(ScheduleItem scheduleItem){

        for (Schedule schedule : this.getAllSchedules())
            schedule.getScheduleItems().remove(scheduleItem);
    }

    public ArrayList<String> getGroupNames() {

        ArrayList<String> groups = new ArrayList<>();

        for (Group group : this.groups)
            if (!groups.contains(group.getName()))
                groups.add(group.getName());

        return groups;
    }

    public Group getGroupByName(String name) {

        for (Group group : this.groups)
            if (group.getName().equals(name))
                return group;

        return null;
    }

    public void addGroup(Group group) {

        this.groups.add(group);
    }

    public Group getGroup(int key) {

        return this.groups.get(key);
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setGroups(ArrayList<Group> groups) {

        this.groups = groups;
    }

    // getters
    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public ArrayList<Group> getGroups() {

        return this.groups;
    }
}
