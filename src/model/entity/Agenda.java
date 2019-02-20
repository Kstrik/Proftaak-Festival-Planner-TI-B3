package model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Agenda {

    private int id;
    private String name;
    private ArrayList<Group> groups;

    // group
    public ArrayList<Group> getAllGroups() {

        return this.groups;
    }

    public ArrayList<String> getAllGroupNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Group group : this.getAllGroups())
            if (!names.contains(group.getName()))
                names.add(group.getName());

        return names;
    }

    public Group getGroupByName(String name) {

        for (Group group : this.getAllGroups())
            if (group.getName().equals(name))
                return group;

        return null;
    }

    // schedule
    public ArrayList<Schedule> getAllSchedules() {

        HashMap<Integer, Schedule> schedules = new HashMap<>();

        for (Group group : this.getAllGroups())
            for (Schedule schedule : group.getSchedules())
                schedules.put(schedule.getId(), schedule);

        return new ArrayList<Schedule>() {{ addAll(schedules.values()); }};
    }

    public Schedule getCombinedScheduleByDate(LocalDateTime date) {

        Schedule combinedSchedule = new Schedule(-1, date);

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.getDate().equals(date))
                combinedSchedule.addScheduleItems(schedule.getScheduleItems());

        return combinedSchedule;
    }

    public Schedule getScheduleByDateFromGroup(Group group, LocalDateTime date) {

        for (Schedule schedule : group.getSchedules())
            if (schedule.getDate().equals(date))
                return schedule;

        return null;
    }

    public ArrayList<LocalDateTime> getAllScheduleDates() {

        ArrayList<LocalDateTime> dates = new ArrayList<>();

        for (Schedule schedule : this.getAllSchedules())
            if (!dates.contains(schedule.getDate()))
                dates.add(schedule.getDate());

        return dates;
    }

    public LocalDateTime getFirstScheduleDate() {

        LocalDateTime firstDate = LocalDateTime.now();

        for (LocalDateTime localDateTime : this.getAllScheduleDates())
            if (firstDate.isAfter(localDateTime))
                firstDate = localDateTime;

        return firstDate;
    }

    public Schedule getFirstSchedule() {

        return this.getCombinedScheduleByDate(this.getFirstScheduleDate());
    }

    // scheduleItem
    public ArrayList<ScheduleItem> getAllScheduleItems() {

        HashMap<Integer, ScheduleItem> scheduleItems = new HashMap<>();

        for (Schedule schedule : this.getAllSchedules())
            for (ScheduleItem scheduleItem : schedule.getScheduleItems())
                    scheduleItems.put(scheduleItem.getId(), scheduleItem);

        return new ArrayList<ScheduleItem>() {{ addAll(scheduleItems.values()); }};
    }

    public String getGroupNameOfScheduleItem(ScheduleItem scheduleItem) {

        for (Group group : this.getAllGroups())
            if (group.containsScheduleItem(scheduleItem))
                return group.getName();

        return null;
    }

    public LocalDateTime getDateOfScheduleItem(ScheduleItem scheduleItem) {

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.containsScheduleItem(scheduleItem))
                return schedule.getDate();

        return null;
    }

    public ScheduleItem getScheduleItemById(int id) {

        for (ScheduleItem scheduleItem : this.getAllScheduleItems())
            if (scheduleItem.getId() == id)
                return scheduleItem;

        return null;
    }

    // classroom
    public ArrayList<Classroom> getAllClassrooms() {

        HashMap<Integer, Classroom> classrooms = new HashMap<>();

        for (ScheduleItem scheduleItem : this.getAllScheduleItems())
            classrooms.put(scheduleItem.getClassroom().getId(), scheduleItem.getClassroom());

        return new ArrayList<Classroom>() {{ addAll(classrooms.values()); }};
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

    public int getAmountOfClassrooms() {

        return this.getAllClassrooms().size();
    }

    public int getClassRoomKey(Classroom searchedClassroom) {

        ArrayList<Classroom> classrooms = this.getAllClassrooms();

        for (Classroom room : classrooms)
            if (room.getId() == searchedClassroom.getId())
                return classrooms.indexOf(room);

        return -1;
    }

    // person
    public ArrayList<Person> getAllPersons() {

        HashMap<Integer, Person> persons = new HashMap<>();

        for (Group group : this.getAllGroups())
            for (Person person : group.getMembers())
                    persons.put(person.getId(), person);

        for (ScheduleItem scheduleItem : this.getAllScheduleItems())
            persons.put(scheduleItem.getTeacher().getId(), scheduleItem.getTeacher());

        return new ArrayList<Person>() {{ addAll(persons.values()); }};
    }

    // student
    public ArrayList<Person> getAllStudents() {

        ArrayList<Person> students = new ArrayList<>();

        for (Person student : this.getAllPersons())
            if (!student.isTeacher())
                students.add(student);

        return students;
    }

    public Person getStudentByName(String name) {

        for (Person student : this.getAllTeachers())
            if (student.getName().equals(name))
                return student;

        return null;
    }

    // teachers
    public ArrayList<Person> getAllTeachers() {

        ArrayList<Person> teachers = new ArrayList<>();

        for (Person teacher : this.getAllPersons())
            if (teacher.isTeacher())
                teachers.add(teacher);

        return teachers;
    }

    public ArrayList<String> getAllTeacherNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Person teacher : this.getAllTeachers())
            if (!names.contains(teacher.getName()))
                names.add(teacher.getName());

        return names;
    }

    public Person getTeacherByName(String name) {

        for (Person teacher : this.getAllTeachers())
            if (teacher.getName().equals(name))
                return teacher;

        return null;
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

    // toString
    @Override
    public String toString() {

        return
            "{\n" +
                "\"id\": \"" + this.id + "\",\n" +
                "\"name\": \"" + this.name + "\",\n" +
                "\"groups\": \"" + this.groupsToString() + "\"\n" +
            "}"
        ;
    }

    private String groupsToString() {

        StringBuilder groups = new StringBuilder();

        for (int i = 0; i < this.groups.size(); i++)
            if (i == (this.groups.size() - 1))
                groups.append(this.groups.get(i).toString()).append("\n");
            else
                groups.append(this.groups.get(i).toString()).append(",\n");

        return
            "[\n" +
                groups.toString() +
            "]"
        ;
    }
}
