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
                combinedSchedule.addItems(schedule.getItems());

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
    public ArrayList<Item> getAllItems() {

        HashMap<Integer, Item> scheduleItems = new HashMap<>();

        for (Schedule schedule : this.getAllSchedules())
            for (Item item : schedule.getItems())
                    scheduleItems.put(item.getId(), item);

        return new ArrayList<Item>() {{ addAll(scheduleItems.values()); }};
    }

    public String getGroupNameOfItem(Item item) {

        for (Group group : this.getAllGroups())
            if (group.containsItem(item))
                return group.getName();

        return null;
    }

    public LocalDateTime getDateOfItem(Item item) {

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.containsItem(item))
                return schedule.getDate();

        return null;
    }

    public Item getItemById(int id) {

        for (Item item : this.getAllItems())
            if (item.getId() == id)
                return item;

        return null;
    }

    // classroom
    public ArrayList<Classroom> getAllClassrooms() {

        HashMap<Integer, Classroom> classrooms = new HashMap<>();

        for (Item item : this.getAllItems())
            classrooms.put(item.getClassroom().getId(), item.getClassroom());

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

        for (Item item : this.getAllItems())
            persons.put(item.getTeacher().getId(), item.getTeacher());

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

        StringBuilder agenda = new StringBuilder();

        agenda.append("{\n");
        agenda.append("\"id\": \"")   .append(this.id)               .append("\",\n");
        agenda.append("\"name\": \"") .append(this.name)             .append("\",\n");
        agenda.append("\"groups\": ") .append(this.groupsToString()) .append("\n");
        agenda.append("}");

        return agenda.toString();
    }

    private String groupsToString() {

        StringBuilder groups = new StringBuilder();

        groups.append("[\n");

        for (int i = 0; i < this.groups.size(); i++)
            if (i == (this.groups.size() - 1))
                groups.append(this.groups.get(i).toString()).append("\n");
            else
                groups.append(this.groups.get(i).toString()).append(",\n");

        groups.append("]");

        return groups.toString();
    }
}
